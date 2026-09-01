package biblioteca;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INÍCIO DO FLUXO DE USO DA BIBLIOTECA ===\n");

        Leitor leitor = new Leitor("123.456.789-00", "Henrique Duarte", "henrique@gmail.com");

        // livro no sistema
        Livro livro1 = new Livro("Dante Alighieri", "Inferno de Dante", 101);
        Livro livro2 = new Livro("Machado de Assis", "Dom Casmurro", 102);
        Livro livro3 = new Livro("George Orwell", "1984", 103);

        Disponibilidade estoque = new Disponibilidade(3);

        // Cadastrando o leitor e os livros
        leitor.cadastroLeitor();
        livro1.cadastroLivro();
        livro2.cadastroLivro();
        livro3.cadastroLivro();

        System.out.println("Estoque inicial: " + estoque.getQuantidade() + " unidade(s).");

        System.out.println("\n--- Reserva ---");
        leitor.reservarLivro(livro1);
        System.out.println("Status do livro 1: " + livro1.getStatus());

        System.out.println("\n--- Empréstimo ---");
        Emprestimo emprestimo = new Emprestimo(1, leitor, livro1, "23/08/2026");
        emprestimo.iniciarEmp();
        estoque.diminuirQuantidade();

        System.out.println("Status do Empréstimo: " + emprestimo.getStatus());
        System.out.println("Status do Livro 1: " + livro1.getStatus());
        System.out.println("Estoque atual: " + estoque.getQuantidade());

        System.out.println("\n--- Testando Proteção de Regra de Negócio ---");
        try {
            System.out.println("Tentando emprestar o mesmo livro novamente...");
            livro1.emprestar();
        } catch (IllegalStateException e) {
            System.out.println("Validação funcionou! Erro capturado: " + e.getMessage());
        }

        System.out.println("\n--- Devolução ---");
        emprestimo.finalizarEmp("30/08/2026");
        estoque.aumentarQuantidade();

        System.out.println("Status final do Empréstimo: " + emprestimo.getStatus());
        System.out.println("Status final do Livro 1: " + livro1.getStatus());
        System.out.println("Estoque restabelecido: " + estoque.getQuantidade() + " unidade(s).");

        System.out.println("\n=== FLUXO CONCLUÍDO COM SUCESSO ===");
    }
}