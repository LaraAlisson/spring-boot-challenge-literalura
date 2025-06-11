package br.lara.alisson.challenge.consultalivros.model;

import jakarta.persistence.*;
import java.util.List; // Importar List para a relação com Autor, embora Livro tenha apenas um autor.

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // O título não pode ser nulo
    private String titulo;

    @ManyToOne // Um autor pode ter muitos livros, um livro tem um autor
    @JoinColumn(name = "autor_id", nullable = false) // Coluna da chave estrangeira, não pode ser nula
    private Autor autor; // Relação com a entidade Autor

    @Column(nullable = false)
    private String idioma;

    private int numeroDownloads;

    // Construtor padrão (necessário para JPA)
    public Livro(){}

    // Construtor para facilitar a criação de objetos Livro
    public Livro(String titulo, String idioma, int numeroDownloads, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
        this.autor = autor;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public int getNumeroDownloads() {
        return numeroDownloads;
    }

    // --- Setters ---
    // Geralmente, o ID não é setado manualmente, mas sim gerado.
    // public void setId(Long id) { this.id = id; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNumeroDownloads(int numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        // Formato para exibição do Livro, incluindo informações do autor.
        String autorNome = (autor != null) ? autor.getNome() : "Desconhecido";
        Integer autorNascimento = (autor != null) ? autor.getAnoNascimento() : null;
        Integer autorFalecimento = (autor != null) ? autor.getAnoFalecimento() : null;

        StringBuilder autorInfo = new StringBuilder(autorNome);
        if (autorNascimento != null) {
            autorInfo.append(" (Nascimento: ").append(autorNascimento);
            if (autorFalecimento != null) {
                autorInfo.append(", Falecimento: ").append(autorFalecimento);
            }
            autorInfo.append(")");
        }

        return "Livro:" +
                "  Título='" + titulo + '\'' +
                ", Autor=" + autorInfo.toString() +
                ", Idioma='" + idioma + '\'' +
                ", Downloads=" + numeroDownloads;
    }
}
