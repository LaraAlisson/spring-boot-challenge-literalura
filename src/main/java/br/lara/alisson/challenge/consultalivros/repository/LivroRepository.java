package br.lara.alisson.challenge.consultalivros.repository;

import br.lara.alisson.challenge.consultalivros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Importar Optional

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Método para buscar um livro pelo título.
    Optional<Livro> findByTitulo(String titulo);

    // Opcional: Para uma verificação mais precisa de duplicidade, você pode usar:
    // Optional<Livro> findByTituloAndAutor(String titulo, Autor autor);
}