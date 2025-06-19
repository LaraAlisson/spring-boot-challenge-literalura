package br.lara.alisson.challenge.consultalivros.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect; // Import necessário para Redirect

public class ConsumoApi {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = null;

        try {

            response = client.send(request, HttpResponse.BodyHandlers.ofString());


            int statusCode = response.statusCode();

            if (statusCode == 200) {
                String json = response.body(); // Obtém o corpo da resposta
                return json;
            } else {
                System.err.println("Erro: A chamada à API falhou com o Código de Status HTTP: " + statusCode);
                System.err.println("Corpo da Resposta para o Erro: " + response.body());
                return null; // Retorna null para indicar falha
            }

        } catch (IOException e) {

            System.err.println("Erro de Rede ou I/O durante a chamada à API: " + e.getMessage());

            throw new RuntimeException("Erro de Rede/I/O: " + e.getMessage(), e);
        } catch (InterruptedException e) {

            System.err.println("Chamada à API interrompida: " + e.getMessage());

            Thread.currentThread().interrupt();
            throw new RuntimeException("Chamada à API interrompida: " + e.getMessage(), e);
        } catch (Exception e) {

            System.err.println("Ocorreu um erro inesperado durante a chamada à API: " + e.getMessage());
            throw new RuntimeException("Erro Inesperado: " + e.getMessage(), e);
        }
    }
}
