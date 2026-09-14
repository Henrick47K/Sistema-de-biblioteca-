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
        if (quantidade <= 0) {
            throw new IllegalStateException("Não há exemplares disponíveis para diminuir.");
        }
        quantidade--;
        atualizarDisponibilidade();
    }

    // --- NOVO: CÁLCULO QUE SERÁ DELEGADO PELA CLASSE TODO ---
    public double calcularTaxaManutencaoEstoque(double custoPorExemplar) {
        if (custoPorExemplar < 0) {
            throw new IllegalArgumentException("O custo por exemplar não pode ser negativo.");
        }
        return this.quantidade * custoPorExemplar;
    }

    public boolean isDisponivel() { return disponivel; }
    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }

        this.quantidade = quantidade;
        atualizarDisponibilidade();
    }
}