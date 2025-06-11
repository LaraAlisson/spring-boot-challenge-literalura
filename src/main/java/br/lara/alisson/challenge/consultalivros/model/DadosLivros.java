package br.lara.alisson.challenge.consultalivros.model;

import com.fasterxml.jackson.annotation.JsonAlias; // Para mapear nomes JSON para campos Java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Para ignorar propriedades JSON que não queremos mapear
import java.util.List; // Para usar listas de autores e idiomas

// @JsonIgnoreProperties(ignoreUnknown = true) é crucial para que o Jackson ignore quaisquer campos
// no JSON da API que não foram declarados neste record. Isso evita erros se a API adicionar novos campos
// no futuro ou ou se houver campos que não nos interessam no momento.
@JsonIgnoreProperties(ignoreUnknown = true)
// Declaração do record DadosLivros.
// Cada parâmetro no cabeçalho do record se torna um componente do record,
// e o Jackson tentará mapeá-lo do JSON.
public record DadosLivros( // Classe renomeada para DadosLivros
                           // @JsonAlias("title") mapeia o campo "title" do JSON para o nosso componente 'titulo' do record.
                           @JsonAlias("title") String titulo,

                           // @JsonAlias("authors") mapeia o campo "authors" do JSON para o nosso componente 'autores'.
                           // Como "authors" é uma lista de objetos no JSON, ele será mapeado para List<DadosAutor>.
                           // A classe 'DadosAutor' (que já criamos como record) será usada para os objetos dentro da lista.
                           @JsonAlias("authors") List<DadosAutor> autores,

                           // @JsonAlias("languages") mapeia o campo "languages" do JSON para o nosso componente 'idiomas'.
                           // Como "languages" é uma lista de strings no JSON, ele será mapeado para List<String>.
                           @JsonAlias("languages") List<String> idiomas,

                           // @JsonAlias("download_count") mapeia o campo "download_count" do JSON para o nosso componente 'numeroDownloads'.
                           @JsonAlias("download_count") Integer numeroDownloads
) {
    // Um record gera automaticamente um construtor, métodos equals(), hashCode() e toString().
    // Podemos personalizar o método toString() aqui para uma saída mais amigável,
    // que é o que será impresso quando você usar System.out.println(dadosLivros);
    @Override
    public String toString() {
        // Obter o nome, ano de nascimento e ano de falecimento do primeiro autor (se houver) para exibição.
        // Usamos um StringBuilder para construir a string do autor de forma eficiente.
        StringBuilder autorInfo = new StringBuilder();
        if (autores != null && !autores.isEmpty()) {
            DadosAutor primeiroAutor = autores.get(0);
            autorInfo.append(primeiroAutor.nome());
            if (primeiroAutor.anoNascimento() != null) {
                autorInfo.append(" (Nascimento: ").append(primeiroAutor.anoNascimento());
                if (primeiroAutor.anoFalecimento() != null) {
                    autorInfo.append(", Falecimento: ").append(primeiroAutor.anoFalecimento());
                }
                autorInfo.append(")");
            }
        } else {
            autorInfo.append("Autor Desconhecido");
        }

        // Obter o primeiro idioma (se houver) para exibição.
        String primeiroIdioma = (idiomas != null && !idiomas.isEmpty()) ? idiomas.get(0) : "Idioma Desconhecido";

        return "Título: '" + titulo + '\'' +
                ", Autor: " + autorInfo.toString() + // Usar a string formatada do autor
                ", Idioma: " + primeiroIdioma +
                ", Downloads: " + numeroDownloads;
    }
}
