package biblioteca;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INÍCIO DO FLUXO DE USO DA BIBLIOTECA ===\n");

        Leitor leitor = new Leitor("123.456.789-00", "Henrique Duarte", "henrique@gmail.com");

        // Livros no sistema (composição com a quantidade de estoque criada internamente)
        Livro livro1 = new Livro("Dante Alighieri", "Inferno de Dante", 101, 3);

        leitor.cadastroLeitor();
        livro1.cadastroLivro();

        System.out.println("Estoque inicial de " + livro1.getTitulo() + ": "
                + livro1.getDisponibilidade().getQuantidade() + " unidade(s).");

        // Teste do cálculo delegado à parte (Disponibilidade)
        double custoTotal = livro1.calcularCustoManutencaoEstoque(15.50);
        System.out.println("Custo estimado de manutenção do estoque do livro: R$ " + custoTotal);

        System.out.println("\n--- Empréstimo ---");
        Emprestimo emprestimo = new Emprestimo(1, leitor, livro1, "23/08/2026");
        emprestimo.iniciarEmp();
        leitor.adicionarEmprestimo(emprestimo);

        System.out.println("Estoque após empréstimo: " + livro1.getDisponibilidade().getQuantidade());

        System.out.println("\n--- Teste de Remoção/Esvaziamento ---");
        livro1.esvaziarEstoque();
        System.out.println("Estoque após esvaziamento: " + livro1.getDisponibilidade().getQuantidade());

        System.out.println("\n=== FLUXO CONCLUÍDO COM SUCESSO ===");
    }
}