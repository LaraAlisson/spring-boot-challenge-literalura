package br.lara.alisson.challenge.consultalivros.repository;

import br.lara.alisson.challenge.consultalivros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

// Interface para operações de persistência da entidade Livro.
public interface LivroRepository extends JpaRepository<Livro, Long> {


    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findByIdioma(String idioma);

    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();
}