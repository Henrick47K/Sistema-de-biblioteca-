package biblioteca;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DA HIERARQUIA E POLIMORFISMO DE LEITORES ===\n");

        // Polimorfismo: referências da superclasse referenciando objetos das subclasses
        Leitor leitor1 = new LeitorEstudante("111.222.333-44", "Henrique Duarte Lima", "henrique@email.com", "EST-2026-01");
        Leitor leitor2 = new LeitorProfessor("555.666.777-88", "Rodrigo Mariotti", "rodrigo@email.com", "Computação");

        // Execução do comportamento especializado
        leitor1.cadastroLeitor();
        System.out.println("Limite de Empréstimos: " + leitor1.getLimiteEmprestimos() + " livros");
        System.out.println("Prazo para Devolução: " + leitor1.getPrazoDevolucaoDias() + " dias\n");

        leitor2.cadastroLeitor();
        System.out.println("Limite de Empréstimos: " + leitor2.getLimiteEmprestimos() + " livros");
        System.out.println("Prazo para Devolução: " + leitor2.getPrazoDevolucaoDias() + " dias\n");

        System.out.println("--- Teste de Reserva de Livro (Sobrescrita e Reutilização) ---");
        Livro livro = new Livro("Dante Alighieri", "Inferno de Dante", 101, 5);

        leitor1.reservarLivro(livro);
        System.out.println();
        leitor2.reservarLivro(livro);

        System.out.println("\n=== EXECUÇÃO CONCLUÍDA COM SUCESSO ===");
    }
}