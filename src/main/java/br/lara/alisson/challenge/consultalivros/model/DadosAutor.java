package br.lara.alisson.challenge.consultalivros.model;


import com.fasterxml.jackson.annotation.JsonAlias; // Importa JsonAlias para mapear nomes JSON diferentes
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Ignora propriedades JSON que não queremos mapear

// @JsonIgnoreProperties(ignoreUnknown = true) é usado para instruir o Jackson a ignorar
// quaisquer propriedades JSON que não estejam explicitamente declaradas neste record.
// Isso ajuda a evitar erros se o JSON da API tiver campos adicionais que não precisamos.
@JsonIgnoreProperties(ignoreUnknown = true)
// Declaração do record DadosAutor.
// Um record é uma classe de dados concisa introduzida no Java 16,
// que automaticamente gera construtor, getters (métodos com o nome do campo),
// equals(), hashCode() e toString().
public record DadosAutor(
        // @JsonAlias("name") mapeia o campo "name" do JSON para o componente 'nome' do record.
        @JsonAlias("name") String nome,

        // @JsonAlias("birth_year") mapeia o campo "birth_year" do JSON para 'anoNascimento'.
        @JsonAlias("birth_year") Integer anoNascimento,

        // @JsonAlias("death_year") mapeia o campo "death_year" do JSON para 'anoFalecimento'.
        @JsonAlias("death_year") Integer anoFalecimento
) {
    // Não precisamos adicionar nenhum código aqui, pois o record já fornece
    // automaticamente os métodos necessários para a funcionalidade básica de dados.
}
