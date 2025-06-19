package br.lara.alisson.challenge.consultalivros.principal;

import br.lara.alisson.challenge.consultalivros.model.Autor;
import br.lara.alisson.challenge.consultalivros.model.DadosAutor;
import br.lara.alisson.challenge.consultalivros.model.DadosLivros;
import br.lara.alisson.challenge.consultalivros.model.DadosRespostaApi;
import br.lara.alisson.challenge.consultalivros.model.Livro;
import br.lara.alisson.challenge.consultalivros.repository.AutorRepository;
import br.lara.alisson.challenge.consultalivros.repository.LivroRepository;
import br.lara.alisson.challenge.consultalivros.service.ConsumoApi;
import br.lara.alisson.challenge.consultalivros.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;


    public void exibirMenu() {
        var opcao = -1;

        while (opcao != 0) {
            System.out.println("\n------------------------------------");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Buscar livro por título na API e salvar no DB");
            System.out.println("2 - Buscar todos os livros no banco de dados");
            System.out.println("3 - Listar autores armazenados com os livros");
            System.out.println("4 - Listar autores vivos em determinado ano");
            System.out.println("5 - Buscar livros pelo idioma");
            System.out.println("6 - Top 10 livros mais baixados"); // Nova opção 6
            System.out.println("0 - Sair");
            System.out.println("------------------------------------");
            System.out.print("Sua escolha: ");

            try {
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1:
                        buscarLivroPeloTitulo();
                        break;
                    case 2:
                        buscarTodosOsLivros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivosPorAno();
                        break;
                    case 5:
                        buscarLivrosPorIdioma();
                        break;
                    case 6:
                        top10LivrosMaisBaixados();
                        break;
                    case 0:
                        System.out.println("Saindo do aplicativo. Até mais!");
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha uma opção de 0 a 6.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("Entrada inválida! Por favor, digite um número.");
                leitura.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    @Transactional
    public void buscarLivroPeloTitulo() {
        System.out.println("\n--- Opção 1: Buscar livro pelo título na API e salvar no DB ---");
        System.out.print("Digite o título do livro para buscar: ");
        var tituloBusca = leitura.nextLine();

        String apiUrl = "https://gutendex.com/books?search=" + tituloBusca.replace(" ", "+");
        System.out.println("Tentando buscar dados para: '" + tituloBusca + "' de " + apiUrl);

        var dadosJson = consumoApi.obterDados(apiUrl);

        if (dadosJson == null || dadosJson.isEmpty()) {
            System.out.println("\n--- Nenhuma string JSON recebida ou ocorreu um erro durante a chamada à API. ---");
            System.out.println("Por favor, verifique a saída do console acima para quaisquer Códigos de Status HTTP ou mensagens de erro.");
            return;
        }

        try {
            DadosRespostaApi respostaApi = conversor.obterDados(dadosJson, DadosRespostaApi.class);

            if (respostaApi != null && respostaApi.resultados() != null && !respostaApi.resultados().isEmpty()) {
                System.out.println("\n--- Livros Encontrados na API e Salvando no Banco de Dados! ---");
                respostaApi.resultados().forEach(dadosLivros -> {
                    Autor autor = null;
                    if (dadosLivros.autores() != null && !dadosLivros.autores().isEmpty()) {
                        DadosAutor dadosAutor = dadosLivros.autores().get(0);
                        String nomeAutor = dadosAutor.nome();

                        Optional<Autor> autorExistente = autorRepository.findByNome(nomeAutor);

                        if (autorExistente.isPresent()) {
                            autor = autorExistente.get();
                            System.out.println("Autor já existe no DB: " + autor.getNome());
                        } else {
                            autor = new Autor(nomeAutor, dadosAutor.anoNascimento(), dadosAutor.anoFalecimento());
                            autorRepository.save(autor);
                            System.out.println("Novo autor salvo no DB: " + autor.getNome());
                        }
                    } else {
                        System.out.println("Livro sem informações de autor para processar.");
                        return;
                    }

                    String idiomaLivro = (dadosLivros.idiomas() != null && !dadosLivros.idiomas().isEmpty()) ? dadosLivros.idiomas().get(0) : "N/A";
                    Livro livro = new Livro(
                            dadosLivros.titulo(),
                            idiomaLivro,
                            dadosLivros.numeroDownloads(),
                            autor
                    );

                    boolean livroJaExiste = livroRepository.findByTitulo(livro.getTitulo()).isPresent();
                    if (livroJaExiste) {
                        System.out.println("Livro '" + livro.getTitulo() + "' já existe no banco de dados. Ignorando.");
                        return;
                    }

                    try {
                        livroRepository.save(livro);
                        System.out.println("Livro salvo no DB: '" + livro.getTitulo() + "' (ID: " + livro.getId() + ")");
                    } catch (Exception e) {
                        System.err.println("Erro ao salvar o livro '" + livro.getTitulo() + "' no DB: " + e.getMessage());
                    }
                    System.out.println("------------------------------------");
                });
            } else {
                System.out.println("\n--- Nenhum livro encontrado para o título '" + tituloBusca + "' na resposta da API. ---");
            }
        } catch (RuntimeException e) {
            System.err.println("Erro ao processar e converter os dados da API: " + e.getMessage());
        }
    }

    public void buscarTodosOsLivros() {
        System.out.println("\n--- Opção 2: Buscar todos os livros no banco de dados ---");
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.");
        } else {
            System.out.println("\n--- Livros no Banco de Dados: ---");
            livros.forEach(livro -> {
                System.out.println(livro);
                System.out.println("------------------------------------");
            });
        }
    }


    @Transactional(readOnly = true)
    public void listarAutores() {
        System.out.println("\n--- Opção 3: Listar autores armazenados com os livros ---");
        List<Autor> autores = autorRepository.findAllWithLivros();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado no banco de dados.");
        } else {
            System.out.println("\n--- Autores no Banco de Dados: ---");
            autores.forEach(autor -> {
                System.out.println("\nAutor: " + autor.getNome());
                System.out.println("Ano de Nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Desconhecido"));
                System.out.println("Ano de Falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo / Desconhecido"));

                if (autor.getLivros() != null && !autor.getLivros().isEmpty()) {
                    System.out.println("Livros deste autor:");
                    autor.getLivros().forEach(livro -> {
                        System.out.println("  - " + livro.getTitulo() + " (Idioma: " + livro.getIdioma() + ")");
                    });
                } else {
                    System.out.println("  Nenhum livro associado a este autor no momento.");
                }
                System.out.println("------------------------------------");
            });
        }
    }


    @Transactional(readOnly = true)
    public void listarAutoresVivosPorAno() {
        System.out.println("\n--- Opção 4: Listar autores vivos em determinado ano ---");
        System.out.print("Digite o ano para verificar autores vivos: ");
        try {
            int ano = leitura.nextInt();
            leitura.nextLine(); // Consome a linha pendente

            List<Autor> autoresVivos = autorRepository.findAutoresVivosByAno(ano);

            if (autoresVivos.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado para o ano " + ano + " no banco de dados.");
            } else {
                System.out.println("\n--- Autores Vivos em " + ano + ": ---");
                autoresVivos.forEach(autor -> {
                    System.out.println("\nAutor: " + autor.getNome());
                    System.out.println("Ano de Nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Desconhecido"));
                    System.out.println("Ano de Falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo / Desconhecido"));
                    System.out.println("------------------------------------");
                });
            }
        } catch (java.util.InputMismatchException e) {
            System.err.println("Entrada inválida! Por favor, digite um número inteiro para o ano.");
            leitura.nextLine(); // Limpa o buffer do scanner
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao buscar autores vivos: " + e.getMessage());
        }
    }


    @Transactional(readOnly = true)
    public void buscarLivrosPorIdioma() {
        System.out.println("\n--- Opção 5: Buscar livros pelo idioma ---");
        System.out.println("Idiomas disponíveis para busca (exemplos):");
        System.out.println("  - PT (Português)");
        System.out.println("  - EN (Inglês)");
        System.out.println("  - FR (Francês)");
        System.out.println("  - ES (Espanhol)");
        System.out.println("  - RU (Russo)");
        System.out.print("Digite o código do idioma (ex: PT, EN): ");
        String idiomaBusca = leitura.nextLine().trim().toLowerCase(); // Normaliza a entrada

        List<Livro> livrosPorIdioma = livroRepository.findByIdioma(idiomaBusca);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("\nNão há livros com o idioma '" + idiomaBusca.toUpperCase() + "' no banco de dados.");
        } else {
            System.out.println("\n--- Livros no Idioma '" + idiomaBusca.toUpperCase() + "': ---");
            livrosPorIdioma.forEach(livro -> {
                System.out.println(livro);
                System.out.println("------------------------------------");
            });
        }
    }

    @Transactional(readOnly = true)
    public void top10LivrosMaisBaixados() {
        System.out.println("\n--- Opção 6: Top 10 livros mais baixados ---");
        // Chama o novo método do repositório para obter os top 10 livros
            List<Livro> topLivros = livroRepository.findTop10ByOrderByNumeroDownloadsDesc();

        if (topLivros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados para listar o Top 10.");
        } else {
            System.out.println("\n--- Top 10 Livros Mais Baixados: ---");
            topLivros.forEach(livro -> {
                System.out.println(livro); // Utiliza o toString() da entidade Livro para formatação
                System.out.println("------------------------------------");
            });
        }
    }

}
