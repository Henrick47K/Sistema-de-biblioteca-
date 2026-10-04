package biblioteca;

public class Revista implements ItemAcervo {
    private String titulo;
    private int edicao;
    private boolean emprestada;

    public Revista(String titulo, int edicao) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser vazio.");
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
        System.out.println("Revista '" + titulo + "' emprestada! Prazo reduzido aplicado.");
    }

    @Override
    public void devolver() {
        if (!emprestada) {
            throw new IllegalStateException("Revista já está disponível.");
        }
        emprestada = false;
        System.out.println("Revista '" + titulo + "' devolvida!");
    }

    @Override
    public void reservar() {
        // Regra de negócio diferente: revistas não podem ser reservadas
        System.out.println("Reserva indisponível: Revistas são apenas para leitura no local ou empréstimo imediato.");
    }

    @Override
    public String getTitulo() {
        return titulo + " (Ed. " + edicao + ")";
    }
}