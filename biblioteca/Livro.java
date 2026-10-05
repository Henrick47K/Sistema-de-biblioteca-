package biblioteca;

import java.util.Objects;

/**
 * Representa um livro no acervo da biblioteca.
 * Implementa ItemAcervo e gerencia sua disponibilidade/estoque diretamente como um atributo.
 */
public class Livro implements ItemAcervo {

    private String autor;
    private String titulo;
    private int codigo;
    private StatusLivro status;
    private int disponibilidade;

    public enum StatusLivro {
        DISPONIVEL,
        EMPRESTADO,
        RESERVADO
    }

    public Livro(String autor, String titulo, int codigo, int disponibilidadeInicial) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor não pode ser nulo ou vazio.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio.");
        }
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código do livro deve ser maior que zero.");
        }
        if (disponibilidadeInicial < 0) {
            throw new IllegalArgumentException("A disponibilidade não pode ser negativa.");
        }

        this.autor = autor;
        this.titulo = titulo;
        this.codigo = codigo;
        this.disponibilidade = disponibilidadeInicial;
        this.status = StatusLivro.DISPONIVEL;
    }

    public Livro(String autor, String titulo, int codigo) {
        this(autor, titulo, codigo, 1);
    }

    public boolean isDisponivel() {
        return this.disponibilidade > 0 && this.status != StatusLivro.EMPRESTADO;
    }

    public void esvaziarEstoque() {
        this.disponibilidade = 0;
    }

    public double calcularCustoManutencaoEstoque(double custoPorExemplar) {
        if (custoPorExemplar < 0) {
            throw new IllegalArgumentException("O custo por exemplar não pode ser negativo.");
        }
        return this.disponibilidade * custoPorExemplar;
    }

    public void cadastroLivro() {
        System.out.println("Livro '" + titulo + "' cadastrado!");
    }

    @Override
    public void emprestar() {
        if (this.status == StatusLivro.EMPRESTADO) {
            throw new IllegalStateException("Livro já está emprestado.");
        }
        if (this.disponibilidade <= 0) {
            throw new IllegalStateException("Não há exemplares disponíveis para empréstimo.");
        }
        this.disponibilidade--;
        this.status = StatusLivro.EMPRESTADO;
        System.out.println("Livro '" + titulo + "' emprestado!");
    }

    @Override
    public void devolver() {
        if (this.status == StatusLivro.DISPONIVEL && this.disponibilidade > 0) {
            throw new IllegalStateException("Livro já está disponível.");
        }
        this.disponibilidade++;
        this.status = StatusLivro.DISPONIVEL;
        System.out.println("Livro '" + titulo + "' devolvido!");
    }

    @Override
    public void reservar() {
        if (this.status == StatusLivro.EMPRESTADO) {
            throw new IllegalStateException("Não é possível reservar um livro emprestado.");
        }
        this.status = StatusLivro.RESERVADO;
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

    @Override
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

    public int getDisponibilidade() { return disponibilidade; }

    public void setDisponibilidade(int disponibilidade) {
        if (disponibilidade < 0) {
            throw new IllegalArgumentException("A disponibilidade não pode ser negativa.");
        }
        this.disponibilidade = disponibilidade;
    }
}