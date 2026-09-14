package biblioteca;

import java.util.Objects;

public class Livro {

    private String autor;
    private String titulo;
    private int codigo;
    private StatusLivro status;

    public enum StatusLivro {
        DISPONIVEL,
        EMPRESTADO,
        RESERVADO
    }

    public Livro(String autor, String titulo, int codigo) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor não pode ser nulo ou vazio.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio.");
        }
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código do livro deve ser maior que zero.");
        }

        this.autor = autor;
        this.titulo = titulo;
        this.codigo = codigo;
        this.status = StatusLivro.DISPONIVEL;
    }

    public void cadastroLivro() {
        System.out.println("Livro '" + titulo + "' cadastrado!");
    }

    public void emprestar() {
        if (this.status == StatusLivro.EMPRESTADO) {
            throw new IllegalStateException("Livro já está emprestado.");
        }
        status = StatusLivro.EMPRESTADO;
        System.out.println("Livro '" + titulo + "' emprestado!");
    }

    public void devolver() {
        if (this.status == StatusLivro.DISPONIVEL) {
            throw new IllegalStateException("Livro já está disponível.");
        }
        status = StatusLivro.DISPONIVEL;
        System.out.println("Livro '" + titulo + "' devolvido!");
    }

    public void reservar() {
        if (this.status == StatusLivro.EMPRESTADO) {
            throw new IllegalStateException("Não é possível reservar um livro emprestado.");
        }
        status = StatusLivro.RESERVADO;
        System.out.println("Livro '" + titulo + "' reservado!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return codigo == livro.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor não pode ser nulo ou vazio.");
        }
        this.autor = autor;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio.");
        }
        this.titulo = titulo;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código do livro deve ser maior que zero.");
        }
        this.codigo = codigo;
    }

    public StatusLivro getStatus() { return status; }
}