package biblioteca.app;

import biblioteca.dominio.*;
import biblioteca.repositorio.EmprestimoRepositorioArquivo;
import biblioteca.repositorio.RepositorioException;
import biblioteca.servico.BibliotecaService;

import java.io.PrintStream;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Camada de apresentação (interface com o usuário).
 * Captura exceções de domínio e técnicas via try/catch para exibir mensagens claras
 * sem encerrar o programa abruptamente diante de erros previsíveis.
 */
public class BibliotecaApp {

    private final BibliotecaService service;
    private final Readable entrada;
    private final PrintStream saida;

    // Construtor padrão para execução normal da aplicação
    public BibliotecaApp(BibliotecaService service) {
        this(service, new StringReader("dados/emprestimos.csv\n"), System.out);
    }

    // Construtor com entrada e saída injetadas (facilita testes automatizados da apresentação)
    public BibliotecaApp(BibliotecaService service, Readable entrada, PrintStream saida) {
        if (service == null || entrada == null || saida == null) {
            throw new IllegalArgumentException("Dependências da apresentação não podem ser nulas.");
        }
        this.service = service;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void iniciar() {
        saida.println("==================================================");
        saida.println("       SISTEMA DE GESTÃO DE BIBLIOTECA");
        saida.println("==================================================\n");

        // try-with-resources garante o fechamento automático do Scanner ao final do bloco
        try (Scanner scanner = new Scanner(entrada)) {
            String arquivoConfig = scanner.hasNextLine() ? scanner.nextLine() : "dados/emprestimos.csv";
            saida.println("Arquivo de persistência ativo: " + arquivoConfig + "\n");

            int proximoId = calcularProximoId();

            Livro livro1 = new Livro("Dante Alighieri", "Inferno de Dante", 101, 1);
            Livro livro2 = new Livro("Robert C. Martin", "Clean Code", 102, 1);
            Livro livro3 = new Livro("Martin Fowler", "Refactoring", 103, 1);
            Revista revista1 = new Revista("National Geographic", 204);

            LeitorEstudante estudante = new LeitorEstudante(
                    "123.456.789-00", "Lucas Silva", "lucas@email.com", "2026001");
            LeitorProfessor professor = new LeitorProfessor(
                    "987.654.321-11", "Dra. Maria Souza", "maria@email.com", "Computação");

            Emprestimo emp1 = executarFluxoNormal(proximoId, estudante, livro1, livro2, revista1);
            demonstrarTratamentoDeErros(proximoId, estudante, professor, livro2, livro3);
            executarDevolucaoEHistorico(emp1, estudante);
        } finally {
            saida.println("\nSessão da biblioteca finalizada com segurança.");
        }
    }

    private int calcularProximoId() {
        return service.listarHistoricoEmprestimos().stream()
                .mapToInt(RegistroEmprestimo::id)
                .max()
                .orElse(0) + 1;
    }

    private Emprestimo executarFluxoNormal(
            int proximoId,
            LeitorEstudante estudante,
            Livro livro1,
            Livro livro2,
            Revista revista1) {
        saida.println("--- 1. FLUXO NORMAL DE OPERAÇÕES ---");
        service.reservarLivro(estudante, livro1);
        saida.println("Livro reservado: " + livro1.getTitulo());

        Emprestimo emp1 = service.realizarEmprestimo(proximoId, estudante, livro1, "26/10/2026");
        service.realizarEmprestimo(proximoId + 1, estudante, livro2, "26/10/2026");
        service.realizarEmprestimo(proximoId + 2, estudante, revista1, "26/10/2026");
        saida.println("Empréstimos ativos de " + estudante.getNome() + ": "
                + estudante.getQuantidadeEmprestimosAtivos() + "/" + estudante.getLimiteEmprestimos() + "\n");
        return emp1;
    }

    private void demonstrarTratamentoDeErros(
            int proximoId,
            LeitorEstudante estudante,
            LeitorProfessor professor,
            Livro livro2,
            Livro livro3) {
        saida.println("--- 2. TRATAMENTO DE ERROS PREVISÍVEIS SEM INTERROMPER O PROGRAMA ---");

        // ERRO PREVISÍVEL 1: Validação na origem (e-mail inválido ao cadastrar leitor)
        try {
            new LeitorEstudante("000.111.222-33", "João Erro", "joao-sem-arroba", "2026999");
        } catch (BibliotecaException e) {
            saida.println("[Erro de Validação Tratado] " + e.getMessage());
        }

        // ERRO PREVISÍVEL 2: Item indisponível (livro2 já está emprestado)
        try {
            service.realizarEmprestimo(proximoId + 3, professor, livro2, "26/10/2026");
        } catch (BibliotecaException e) {
            saida.println("[Erro de Acervo Tratado] " + e.getMessage());
        }

        // ERRO PREVISÍVEL 3: Limite de empréstimos excedido (estudante já tem 3 ativos)
        try {
            service.realizarEmprestimo(proximoId + 4, estudante, livro3, "26/10/2026");
        } catch (BibliotecaException e) {
            saida.println("[Erro de Limite Tratado] " + e.getMessage());
        }

        // ERRO PREVISÍVEL 4: Erro técnico de repositório (tentativa de ler um diretório como CSV)
        try {
            EmprestimoRepositorioArquivo repoFalho = new EmprestimoRepositorioArquivo(Path.of("."));
            repoFalho.listarTodos();
        } catch (RepositorioException e) {
            saida.println("[Erro Técnico Tratado] " + e.getMessage());
        }
    }

    private void executarDevolucaoEHistorico(Emprestimo emp1, LeitorEstudante estudante) {
        saida.println("\n--- 3. DEVOLUÇÃO E CONSULTA AO HISTÓRICO PERSISTIDO ---");
        service.finalizarEmprestimo(emp1, "02/11/2026");
        saida.println("Devolução concluída para o empréstimo ID " + emp1.getId()
                + ". Empréstimos ativos restantes de " + estudante.getNome() + ": "
                + estudante.getQuantidadeEmprestimosAtivos());

        for (RegistroEmprestimo reg : service.listarHistoricoEmprestimos()) {
            saida.println("  -> " + reg);
        }
    }
}