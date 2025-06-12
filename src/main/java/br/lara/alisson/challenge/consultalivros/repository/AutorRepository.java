package br.lara.alisson.challenge.consultalivros.repository;

import br.lara.alisson.challenge.consultalivros.model.Autor;
import br.lara.alisson.challenge.consultalivros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;


public interface AutorRepository extends JpaRepository<Autor, Long> {


    Optional<Autor> findByNome(String nome);

    @Query("SELECT a FROM Autor a JOIN FETCH a.livros")
    List<Autor> findAllWithLivros();

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosByAno(Integer ano);
}