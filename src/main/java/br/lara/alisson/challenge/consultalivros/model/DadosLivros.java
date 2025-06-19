package br.lara.alisson.challenge.consultalivros.model;

import com.fasterxml.jackson.annotation.JsonAlias; // Para mapear nomes JSON para campos Java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Para ignorar propriedades JSON que não queremos mapear
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosLivros(@JsonAlias("title") String titulo,
                           @JsonAlias("authors") List<DadosAutor> autores,
                           @JsonAlias("languages") List<String> idiomas,
                           @JsonAlias("download_count") Integer numeroDownloads
) {
    @Override
    public String toString() {
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

        String primeiroIdioma = (idiomas != null && !idiomas.isEmpty()) ? idiomas.get(0) : "Idioma Desconhecido";

        return "Título: '" + titulo + '\'' +
                ", Autor: " + autorInfo.toString() + // Usar a string formatada do autor
                ", Idioma: " + primeiroIdioma +
                ", Downloads: " + numeroDownloads;
    }
}
