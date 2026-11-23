package biblioteca.dominio;

import java.util.Objects;

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
        ValidadorDominio.exigirTextoNaoVazio(autor, "Autor não pode ser nulo ou vazio.");
        ValidadorDominio.exigirTextoNaoVazio(titulo, "Título não pode ser nulo ou vazio.");
        ValidadorDominio.exigirMaiorQueZero(codigo, "O código do livro deve ser maior que zero.");
        ValidadorDominio.exigirNaoNegativo(disponibilidadeInicial, "A disponibilidade não pode ser negativa.");

        this.autor = autor;
        this.titulo = titulo;
        this.codigo = codigo;
        this.disponibilidade = disponibilidadeInicial;
        this.status = StatusLivro.DISPONIVEL;
    }

    public Livro(String autor, String titulo, int codigo) {
        this(autor, titulo, codigo, 1);
    }

    @Override
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
    }

    @Override
    public void devolver() {
        if (this.status == StatusLivro.DISPONIVEL && this.disponibilidade > 0) {
            throw new IllegalStateException("Livro já está disponível.");
        }
        this.disponibilidade++;
        this.status = StatusLivro.DISPONIVEL;
    }

    @Override
    public void reservar() {
        if (this.status == StatusLivro.EMPRESTADO) {
            throw new IllegalStateException("Não é possível reservar um livro emprestado.");
        }
        this.status = StatusLivro.RESERVADO;
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
        ValidadorDominio.exigirTextoNaoVazio(autor, "Autor não pode ser nulo ou vazio.");
        this.autor = autor;
    }

    @Override
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        ValidadorDominio.exigirTextoNaoVazio(titulo, "Título não pode ser nulo ou vazio.");
        this.titulo = titulo;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) {
        ValidadorDominio.exigirMaiorQueZero(codigo, "O código do livro deve ser maior que zero.");
        this.codigo = codigo;
    }

    public StatusLivro getStatus() { return status; }
    public int getDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(int disponibilidade) {
        ValidadorDominio.exigirNaoNegativo(disponibilidade, "A disponibilidade não pode ser negativa.");
        this.disponibilidade = disponibilidade;
    }
}