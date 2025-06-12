package br.lara.alisson.challenge.consultalivros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosRespostaApi(
        @JsonAlias("results") List<DadosLivros> resultados ){}
