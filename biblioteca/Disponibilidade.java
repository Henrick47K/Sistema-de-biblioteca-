package biblioteca;

public class Disponibilidade {

    private boolean disponivel;
    private int quantidade;

    public Disponibilidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }

        this.quantidade = quantidade;
        atualizarDisponibilidade();
    }

    public void verificarDisponibilidade() {
        if (disponivel) {
            System.out.println("Livro disponível!");
        } else {
            System.out.println("Livro indisponível!");
        }
    }

    public void atualizarDisponibilidade() {
        disponivel = quantidade > 0;
    }

    public void aumentarQuantidade() {
        quantidade++;
        atualizarDisponibilidade();
    }

    public void diminuirQuantidade() {
        if (quantidade > 0) {
            quantidade--;
        }
        atualizarDisponibilidade();
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }

        this.quantidade = quantidade;
        atualizarDisponibilidade();
    }
}