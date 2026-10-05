package biblioteca;

/**
 * Representa uma revista/periódico no acervo da biblioteca.
 * Implementa ItemAcervo com regras de negócio próprias (sem suporte a reserva prévia).
 */
public class Revista implements ItemAcervo {

    private String titulo;
    private int edicao;
    private boolean emprestada;

    public Revista(String titulo, int edicao) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio.");
        }
        if (edicao <= 0) {
            throw new IllegalArgumentException("Número da edição deve ser maior que zero.");
        }
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
        System.out.println("Revista '" + getTitulo() + "' emprestada! Prazo reduzido aplicado.");
    }

    @Override
    public void devolver() {
        if (!emprestada) {
            throw new IllegalStateException("Revista já está disponível.");
        }
        emprestada = false;
        System.out.println("Revista '" + getTitulo() + "' devolvida!");
    }

    @Override
    public void reservar() {
        System.out.println("Reserva indisponível: Revistas são apenas para leitura no local ou empréstimo imediato.");
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