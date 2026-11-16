package biblioteca.app;

import biblioteca.dominio.*;
import biblioteca.repositorio.EmprestimoRepositorio;
import biblioteca.repositorio.EmprestimoRepositorioArquivo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SISTEMA DE GESTÃO DE BIBLIOTECA - FLUXO PONTA A PONTA");
        System.out.println("==================================================\n");

        // Único ponto de escolha da implementação concreta do repositório (Injeção por Construtor)
        EmprestimoRepositorio repositorio = new EmprestimoRepositorioArquivo(Path.of("dados", "emprestimos.csv"));
        BibliotecaService bibliotecaService = new BibliotecaService(repositorio);

        // 1. CADASTRO DO ACERVO
        System.out.println("--- 1. CADASTRO DO ACERVO ---");
        Livro livro1 = new Livro("Dante Alighieri", "Inferno de Dante", 101, 2);
        Livro livro2 = new Livro("Robert C. Martin", "Clean Code", 102, 1);
        Revista revista1 = new Revista("National Geographic", 204);
        Revista revista2 = new Revista("Superinteressante", 450);

        livro1.cadastroLivro();
        livro2.cadastroLivro();
        System.out.println();

        // Lista polimórfica de itens do acervo
        List<ItemAcervo> acervo = new ArrayList<>();
        acervo.add(livro1);
        acervo.add(livro2);
        acervo.add(revista1);
        acervo.add(revista2);

        // 2. CADASTRO DE LEITORES (Herança e Especialização)
        System.out.println("--- 2. CADASTRO DE LEITORES ---");
        LeitorEstudante estudante = new LeitorEstudante("123.456.789-00", "Lucas Silva", "lucas@email.com", "2026001");
        LeitorProfessor professor = new LeitorProfessor("987.654.321-11", "Dra. Maria Souza", "maria@email.com", "Computação");

        estudante.cadastroLeitor();
        professor.cadastroLeitor();
        System.out.println("Limite Estudante: " + estudante.getLimiteEmprestimos() + " livros | Prazo: " + estudante.getPrazoDevolucaoDias() + " dias");
        System.out.println("Limite Professor: " + professor.getLimiteEmprestimos() + " livros | Prazo: " + professor.getPrazoDevolucaoDias() + " dias\n");

        // 3. RESERVA DE LIVROS
        System.out.println("--- 3. RESERVA DE LIVROS ---");
        estudante.reservarLivro(livro1);
        System.out.println();

        // 4. REALIZAÇÃO DE EMPRÉSTIMOS VIA SERVIÇO (Persistindo no Repositório)
        System.out.println("--- 4. EXECUÇÃO DE EMPRÉSTIMOS ---");
        Emprestimo emp1 = bibliotecaService.realizarEmprestimo(1, estudante, livro1, "26/10/2026");
        Emprestimo emp2 = bibliotecaService.realizarEmprestimo(2, professor, livro2, "26/10/2026");
        Emprestimo emp3 = bibliotecaService.realizarEmprestimo(3, estudante, revista1, "26/10/2026");
        System.out.println();

        // 5. CONSULTA DE EMPRÉSTIMOS DO LEITOR
        System.out.println("--- 5. CONSULTA DE EMPRÉSTIMOS ATIVOS DO LEITOR ---");
        System.out.println("Empréstimos de " + estudante.getNome() + ": " + estudante.getEmprestimos().size());
        Emprestimo empBuscado = estudante.buscarEmprestimoPorId(1);
        if (empBuscado != null) {
            System.out.println("  -> Encontrado Empréstimo ID " + empBuscado.getId() + " - Item: " + empBuscado.getItem().getTitulo() + " (Status: " + empBuscado.getStatus() + ")");
        }
        System.out.println();

        // 6. FINALIZAÇÃO E DEVOLUÇÃO DO EMPRÉSTIMO VIA SERVIÇO
        System.out.println("--- 6. DEVOLUÇÃO E FINALIZAÇÃO DE EMPRÉSTIMO ---");
        bibliotecaService.finalizarEmprestimo(emp1, "02/11/2026");
        bibliotecaService.finalizarEmprestimo(emp3, "28/10/2026");
        System.out.println();

        // 7. LISTAGEM DOS REGISTROS PERSISTIDOS NO REPOSITÓRIO
        System.out.println("--- 7. HISTÓRICO PERSISTIDO NO REPOSITÓRIO ---");
        for (RegistroEmprestimo reg : bibliotecaService.listarHistoricoEmprestimos()) {
            System.out.println("  -> " + reg);
        }
    }
}