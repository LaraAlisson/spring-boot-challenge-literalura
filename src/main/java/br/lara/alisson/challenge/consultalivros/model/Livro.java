package br.lara.alisson.challenge.consultalivros.model;

import jakarta.persistence.*;


@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(nullable = false)
    private String idioma;

    private int numeroDownloads;

    public Livro(){}

    public Livro(String titulo, String idioma, int numeroDownloads, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
        this.autor = autor;
    }

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
        String autorNome = (autor != null) ? autor.getNome() : "Desconhecido";

        // Formato da saída para corresponder à imagem fornecida.
        return "--- LIVRO ---\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autorNome + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de downloads: " + numeroDownloads + ".0\n" +
                "--------------------";
    }
}
