package biblioteca.dominio;

/**
 * Representa uma revista/periódico no acervo da biblioteca.
 * Implementa ItemAcervo com regras de negócio próprias (sem suporte a reserva prévia).
 */
public class Revista implements ItemAcervo {

    private String titulo;
    private int edicao;
    private boolean emprestada;

    public Revista(String titulo, int edicao) {
        ValidadorDominio.exigirTextoNaoVazio(titulo, "Título não pode ser nulo ou vazio.");
        ValidadorDominio.exigirMaiorQueZero(edicao, "Número da edição deve ser maior que zero.");
        this.titulo = titulo;
        this.edicao = edicao;
        this.emprestada = false;
    }

    @Override
    public void emprestar() {
        if (emprestada) {
            throw new IllegalStateException("Revista já está emprestada.");
        }
        emprestada = true;
    }

    @Override
    public void devolver() {
        if (!emprestada) {
            throw new IllegalStateException("Revista já está disponível.");
        }
        emprestada = false;
    }

    @Override
    public void reservar() {
        // Regra de negócio: revistas são para leitura local ou empréstimo imediato, sem reserva prévia.
    }

    @Override
    public boolean isDisponivel() {
        // A revista está disponível sempre que não estiver emprestada no momento.
        return !emprestada;
    }

    @Override
    public String getTitulo() {
        return titulo + " (Edição " + edicao + ")";
    }

    public int getEdicao() {
        return edicao;
    }

    public boolean isEmprestada() {
        return emprestada;
    }
}