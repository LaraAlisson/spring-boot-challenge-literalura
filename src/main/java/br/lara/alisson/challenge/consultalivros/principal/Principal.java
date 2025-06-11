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
import org.springframework.transaction.annotation.Transactional; // <<<<<< IMPORTANTE: Nova importação
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
            System.out.println("0 - Sair");
            System.out.println("------------------------------------");
            System.out.print("Sua escolha: ");

            try {
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1:
                        buscarLivroPeloTitulo(); // Agora também salva no DB
                        break;
                    case 2:
                        System.out.println("Funcionalidade 'Buscar todos os livros no banco de dados' ainda não implementada.");
                        // TODO: Implementar busca no DB
                        break;
                    case 3:
                        System.out.println("Funcionalidade 'Listar autores armazenados' ainda não implementada.");
                        // TODO: Implementar listagem de autores
                        break;
                    case 4:
                        System.out.println("Funcionalidade 'Listar autores vivos por ano' ainda não implementada.");
                        // TODO: Implementar busca de autores vivos por ano
                        break;
                    case 5:
                        System.out.println("Funcionalidade 'Buscar livros pelo idioma' ainda não implementada.");
                        // TODO: Implementar busca por idioma
                        break;
                    case 0:
                        System.out.println("Saindo do aplicativo. Até mais!");
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha uma opção de 0 a 5.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("Entrada inválida! Por favor, digite um número.");
                leitura.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                // e.printStackTrace();
            }
        }
    }

    @Transactional // <<<<<< IMPORTANTE: Esta anotação fará com que as operações de DB sejam comitadas
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
                            autorRepository.save(autor); // Salva o novo autor
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
                            autor // Associa o autor (existente ou recém-salvo)
                    );

                    // Verifica se o livro já existe no banco antes de salvar
                    // Isso é uma medida extra para evitar duplicatas de livros se a API retornar o mesmo.
                    // Uma forma simples seria buscar por título e autor, mas vamos simplificar aqui:
                    // (Você pode adicionar uma lógica mais robusta depois, se necessário)
                    boolean livroJaExiste = livroRepository.findByTitulo(livro.getTitulo()).isPresent(); // Precisamos criar findByTitulo em LivroRepository
                    if (livroJaExiste) {
                        System.out.println("Livro '" + livro.getTitulo() + "' já existe no banco de dados. Ignorando.");
                        return; // Pula para o próximo livro
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

}
