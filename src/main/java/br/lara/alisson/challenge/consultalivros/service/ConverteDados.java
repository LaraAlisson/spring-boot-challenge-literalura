package br.lara.alisson.challenge.consultalivros.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            throw new RuntimeException("Falha ao converter dados JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado durante a conversão de JSON: " + e.getMessage());
            throw new RuntimeException("Erro inesperado na conversão de JSON: " + e.getMessage(), e);
        }
    }
}
