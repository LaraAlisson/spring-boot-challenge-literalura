package br.lara.alisson.challenge.consultalivros.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
