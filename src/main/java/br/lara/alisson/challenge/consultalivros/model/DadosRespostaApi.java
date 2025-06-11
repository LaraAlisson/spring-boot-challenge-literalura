package br.lara.alisson.challenge.consultalivros.model;

import com.fasterxml.jackson.annotation.JsonAlias; // Para mapear nomes JSON para campos Java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Para ignorar propriedades JSON que não queremos mapear
import java.util.List; // Para usar a lista de DadosLivros

// @JsonIgnoreProperties(ignoreUnknown = true) é essencial aqui para ignorar campos como "count", "next", "previous"
// que a API Gutendex pode retornar, mas que não nos interessam no momento.
@JsonIgnoreProperties(ignoreUnknown = true)
// Declaração do record DadosRespostaApi.
public record DadosRespostaApi(
        // @JsonAlias("results") mapeia o campo "results" do JSON para o nosso componente 'resultados'.
        // Este componente é uma List de DadosLivros, que é a classe que acabamos de criar.
        @JsonAlias("results") List<DadosLivros> resultados // Mapeia a lista de livros
) {
    // Para um record simples como este, o construtor, getters e toString() são gerados automaticamente
    // e geralmente são suficientes. Não precisamos de personalizações extras agora.
}
