package br.lara.alisson.challenge.consultalivros.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {
    // ObjectMapper é a principal classe do Jackson para serialização/desserialização de JSON
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Converte uma string JSON para um objeto da classe especificada.
     *
     * @param json A string JSON a ser convertida.
     * @param classe A classe para a qual o JSON deve ser convertido.
     * @param <T> O tipo do objeto de retorno.
     * @return Um objeto da classe especificada preenchido com os dados do JSON.
     * @throws RuntimeException se ocorrer um erro durante o processamento do JSON.
     */
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            // Usa o ObjectMapper para ler o JSON e mapeá-lo para a classe fornecida
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            // Captura exceções de processamento de JSON (ex: JSON malformado, mapeamento incorreto)
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            throw new RuntimeException("Falha ao converter dados JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            // Captura qualquer outra exceção inesperada
            System.err.println("Ocorreu um erro inesperado durante a conversão de JSON: " + e.getMessage());
            throw new RuntimeException("Erro inesperado na conversão de JSON: " + e.getMessage(), e);
        }
    }
}
