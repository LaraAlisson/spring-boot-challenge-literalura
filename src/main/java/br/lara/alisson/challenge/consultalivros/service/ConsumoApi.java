package br.lara.alisson.challenge.consultalivros.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect; // Import necessário para Redirect

public class ConsumoApi {
    /**
     * Faz uma requisição HTTP GET para o URL especificado e retorna o corpo da resposta como String.
     * Inclui tratamento de erros aprimorado e saída detalhada no console para depuração.
     *
     * @param endereco O URL de onde buscar os dados.
     * @return O corpo da resposta como String se a requisição for bem-sucedida (HTTP 200), caso contrário, null.
     */
    public String obterDados(String endereco) {
        // Cria uma nova instância do cliente HTTP
        // Configura o cliente para seguir redirecionamentos automaticamente (ex: 301, 302)
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(Redirect.ALWAYS) // Adiciona esta linha para seguir redirecionamentos
                .build();

        // Constrói a requisição HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco)) // Define o URI para a requisição
                .build(); // Constrói a requisição

        HttpResponse<String> response = null; // Inicializa a resposta como null

        try {
            // Envia a requisição e obtém a resposta
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Obtém o código de status HTTP da resposta
            int statusCode = response.statusCode();
//            System.out.println("--- Detalhes da Chamada à API ---");
//            System.out.println("URL Requisitado: " + endereco);
//            System.out.println("Código de Status HTTP Recebido: " + statusCode);

            // Verifica se o código de status indica sucesso (200 OK)
            if (statusCode == 200) {
                String json = response.body(); // Obtém o corpo da resposta
//                System.out.println("Corpo da Resposta da API (JSON):");
//                System.out.println(json); // Imprime a resposta JSON bruta
                return json; // Retorna a string JSON
            } else {
                // Se o código de status não for 200, é um erro
                System.err.println("Erro: A chamada à API falhou com o Código de Status HTTP: " + statusCode);
                System.err.println("Corpo da Resposta para o Erro: " + response.body());
                return null; // Retorna null para indicar falha
            }

        } catch (IOException e) {
            // Captura exceções relacionadas a I/O (ex: problemas de rede, URL inválido)
            System.err.println("Erro de Rede ou I/O durante a chamada à API: " + e.getMessage());
            // Re-lança como RuntimeException para garantir que o método chamador a trate ou o programa pare adequadamente
            throw new RuntimeException("Erro de Rede/I/O: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            // Captura interrupções (ex: se a thread esperando pela resposta for interrompida)
            System.err.println("Chamada à API interrompida: " + e.getMessage());
            // Restaura o status de interrupção e re-lança
            Thread.currentThread().interrupt();
            throw new RuntimeException("Chamada à API interrompida: " + e.getMessage(), e);
        } catch (Exception e) {
            // Captura quaisquer outras exceções inesperadas
            System.err.println("Ocorreu um erro inesperado durante a chamada à API: " + e.getMessage());
            throw new RuntimeException("Erro Inesperado: " + e.getMessage(), e);
        }
    }
}
