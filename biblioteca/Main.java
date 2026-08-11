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


        // Leitor reserva o livro
        leitor.reservarLivro();


        // Verificando disponibilidade
        Disponibilidade disponibilidade = new Disponibilidade(1);

        disponibilidade.verificarDisponibilidade();


        // Realizando o empréstimo
        Emprestimo emprestimo = new Emprestimo(
                1,
                "10/08/2026"
        );

        emprestimo.iniciarEmp();

        livro.emprestar();

        disponibilidade.diminuirQuantidade();

        System.out.println("Livro emprestado para: " + leitor.getNome());
        System.out.println("Nome do livro: " + livro.getTitulo());

        disponibilidade.verificarDisponibilidade();


        // Devolvendo o livro
        emprestimo.setDataDev("17/08/2026");

        livro.devolver();

        disponibilidade.aumentarQuantidade();

        emprestimo.finalizarEmp();

        System.out.println("Livro devolvido por: " + leitor.getNome());

        disponibilidade.verificarDisponibilidade();
    }
}