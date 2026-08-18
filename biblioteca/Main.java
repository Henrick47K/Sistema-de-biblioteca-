package biblioteca;

public class Main {

    public static void main(String[] args) {

        // Criando o leitor
        Leitor leitor = new Leitor(
                "123456789",
                "Henrique",
                "henrique@gmail.com"
        );
        leitor.cadastroLeitor();

        // Criando o livro
        Livro livro = new Livro(
                "Dante Alighieri",
                "Inferno de Dante",
                1
        );
        livro.cadastroLivro();

        // Leitor reserva o livro (atualiza o estado do livro para RESERVADO)
        leitor.reservarLivro(livro);
        System.out.println("Status do Livro após reserva: " + livro.getStatus());

        // Verificando disponibilidade inicial
        Disponibilidade disponibilidade = new Disponibilidade(1);
        disponibilidade.verificarDisponibilidade();

        // Realizando o empréstimo (associa o Leitor e o Livro)
        Emprestimo emprestimo = new Emprestimo(1, leitor, livro, "10/08/2026");
        emprestimo.iniciarEmp();
        disponibilidade.diminuirQuantidade();

        System.out.println("Livro emprestado para: " + emprestimo.getLeitor().getNome());
        System.out.println("Nome do livro: " + emprestimo.getLivro().getTitulo());
        System.out.println("Status do Empréstimo: " + emprestimo.getStatus());
        System.out.println("Status do Livro: " + livro.getStatus());
        disponibilidade.verificarDisponibilidade();

        // Devolvendo o livro
        emprestimo.finalizarEmp("17/08/2026");
        disponibilidade.aumentarQuantidade();

        System.out.println("Livro devolvido por: " + emprestimo.getLeitor().getNome());
        System.out.println("Status do Empréstimo: " + emprestimo.getStatus());
        System.out.println("Status do Livro: " + livro.getStatus());
        disponibilidade.verificarDisponibilidade();
    }
}