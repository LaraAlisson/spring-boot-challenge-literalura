package br.lara.alisson.challenge.consultalivros.repository;

import br.lara.alisson.challenge.consultalivros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Importar Optional

public interface AutorRepository extends JpaRepository<Autor, Long>{
    Optional<Autor> findByNome(String nome);
}
