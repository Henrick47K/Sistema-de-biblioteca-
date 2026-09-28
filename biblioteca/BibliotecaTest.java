package biblioteca;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaTest {

    // --- TESTES DE HERANÇA DE VALIDAÇÃO E ATRIBUTOS PRÓPRIOS ---

    @Test
    void testHerancaDeValidacaoEAtributosProprios() {
        // Validação herdada da superclasse Leitor executada através do super no construtor
        assertThrows(IllegalArgumentException.class, () -> {
            new LeitorEstudante("", "Henrique", "henrique@gmail.com", "2026001");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new LeitorProfessor("123", "", "henrique@gmail.com", "Computação");
        });

        // Validação dos atributos próprios das subclasses
        assertThrows(IllegalArgumentException.class, () -> {
            new LeitorEstudante("123", "Henrique", "henrique@gmail.com", "");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new LeitorProfessor("123", "Henrique", "henrique@gmail.com", null);
        });
    }

    // --- TESTES DE ESPECIALIZAÇÃO DE COMPORTAMENTO ---

    @Test
    void testEspecializacaoLimitesEPrazos() {
        Leitor estudante = new LeitorEstudante("123", "Lucas", "lucas@email.com", "EST123");
        Leitor professor = new LeitorProfessor("456", "Maria", "maria@email.com", "Exatas");

        assertEquals(3, estudante.getLimiteEmprestimos());
        assertEquals(14, estudante.getPrazoDevolucaoDias());

        assertEquals(10, professor.getLimiteEmprestimos());
        assertEquals(30, professor.getPrazoDevolucaoDias());
    }

    @Test
    void testRespeitoAoLimiteEspecializadoDeEmprestimos() {
        LeitorEstudante estudante = new LeitorEstudante("123", "Lucas", "lucas@email.com", "EST123");
        Livro livro = new Livro("Autor", "Título", 100, 10);

        Emprestimo emp1 = new Emprestimo(1, estudante, livro, "01/09/2026");
        Emprestimo emp2 = new Emprestimo(2, estudante, livro, "01/09/2026");
        Emprestimo emp3 = new Emprestimo(3, estudante, livro, "01/09/2026");
        Emprestimo emp4 = new Emprestimo(4, estudante, livro, "01/09/2026");

        estudante.adicionarEmprestimo(emp1);
        estudante.adicionarEmprestimo(emp2);
        estudante.adicionarEmprestimo(emp3);

        // O 4º empréstimo ultrapassa o limite de 3 do estudante
        assertThrows(IllegalStateException.class, () -> {
            estudante.adicionarEmprestimo(emp4);
        });
    }

    @Test
    void testReutilizacaoDeMetodosHerdados() {
        LeitorProfessor professor = new LeitorProfessor("456", "Maria", "maria@email.com", "Exatas");
        Livro livro = new Livro("Autor", "Título", 101, 5);
        Emprestimo emp = new Emprestimo(1, professor, livro, "01/09/2026");

        // Métodos de coleção herdados diretamente de Leitor
        professor.adicionarEmprestimo(emp);
        assertEquals(1, professor.getEmprestimos().size());
        assertEquals(emp, professor.buscarEmprestimoPorId(1));

        assertTrue(professor.removerEmprestimo(1));
        assertEquals(0, professor.getEmprestimos().size());
    }

    // --- TESTES ORIGINAIS DAS ETAPAS ANTERIORES PRESERVADOS ---

    @Test
    void testConstrutoresInvalidosLancamExcecao() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Leitor("", "Henrique", "henrique@gmail.com");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro("Dante Alighieri", "Inferno de Dante", 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Disponibilidade(-5);
        });

        Leitor leitorValido = new Leitor("123", "Henrique", "henrique@gmail.com");
        Livro livroValido = new Livro("Dante Alighieri", "Inferno de Dante", 1);

        assertThrows(IllegalArgumentException.class, () -> {
            new Emprestimo(0, leitorValido, livroValido, "17/08/2026");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Emprestimo(1, null, livroValido, "17/08/2026");
        });
    }

    @Test
    void testAlteracaoDeEstadoDoLivro() {
        Livro livro = new Livro("Dante Alighieri", "Inferno de Dante", 101);

        assertEquals(Livro.StatusLivro.DISPONIVEL, livro.getStatus());

        livro.reservar();
        assertEquals(Livro.StatusLivro.RESERVADO, livro.getStatus());

        livro.emprestar();
        assertEquals(Livro.StatusLivro.EMPRESTADO, livro.getStatus());

        livro.devolver();
        assertEquals(Livro.StatusLivro.DISPONIVEL, livro.getStatus());
    }

    @Test
    void testInvarianteDisponibilidadeEEmprestimo() {
        Disponibilidade disp = new Disponibilidade(1);
        assertTrue(disp.isDisponivel());

        disp.diminuirQuantidade();
        assertEquals(0, disp.getQuantidade());
        assertFalse(disp.isDisponivel());

        Leitor leitor = new Leitor("123", "Henrique", "henrique@gmail.com");
        Livro livro = new Livro("Dante Alighieri", "Inferno de Dante", 101);

        Emprestimo emp = new Emprestimo(1, leitor, livro, "17/08/2026");
        emp.iniciarEmp();
        assertEquals(Emprestimo.StatusEmprestimo.ATIVO, emp.getStatus());
        assertEquals(Livro.StatusLivro.EMPRESTADO, livro.getStatus());

        emp.finalizarEmp("24/08/2026");
        assertEquals(Emprestimo.StatusEmprestimo.FINALIZADO, emp.getStatus());
        assertEquals(Livro.StatusLivro.DISPONIVEL, livro.getStatus());
    }

    @Test
    void testValidaTransicoesDeEstadoInvalidas() {
        Disponibilidade disp = new Disponibilidade(0);
        assertThrows(IllegalStateException.class, disp::diminuirQuantidade);

        Livro livro = new Livro("Dante Alighieri", "Inferno de Dante", 101);
        livro.emprestar();
        assertThrows(IllegalStateException.class, livro::emprestar);

        Leitor leitor = new Leitor("123", "Henrique", "henrique@gmail.com");
        Emprestimo emp = new Emprestimo(1, leitor, livro, "17/08/2026");
        emp.finalizarEmp("24/08/2026");
        assertThrows(IllegalStateException.class, () -> emp.finalizarEmp("25/08/2026"));
    }

    @Test
    void testInclusaoEConsultaEmprestimo() {
        Leitor leitor = new Leitor("123", "Henrique", "henrique@gmail.com");
        Livro livro = new Livro("Dante Alighieri", "Inferno", 101);
        Emprestimo emp = new Emprestimo(1, leitor, livro, "17/08/2026");

        leitor.adicionarEmprestimo(emp);

        assertEquals(1, leitor.getEmprestimos().size());
        assertEquals(emp, leitor.buscarEmprestimoPorId(1));
        assertNull(leitor.buscarEmprestimoPorId(999));
    }

    @Test
    void testImpedirDuplicidades() {
        Leitor leitor = new Leitor("123", "Henrique", "henrique@gmail.com");
        Livro livro = new Livro("Dante Alighieri", "Inferno", 101);
        Emprestimo emp1 = new Emprestimo(1, leitor, livro, "17/08/2026");
        Emprestimo empDuplicado = new Emprestimo(1, leitor, livro, "17/08/2026");

        leitor.adicionarEmprestimo(emp1);

        assertThrows(IllegalStateException.class, () -> {
            leitor.adicionarEmprestimo(empDuplicado);
        });
    }

    @Test
    void testRemocaoEmprestimo() {
        Leitor leitor = new Leitor("123", "Henrique", "henrique@gmail.com");
        Livro livro = new Livro("Dante Alighieri", "Inferno", 101);
        Emprestimo emp = new Emprestimo(1, leitor, livro, "17/08/2026");

        leitor.adicionarEmprestimo(emp);
        assertTrue(leitor.removerEmprestimo(1));
        assertEquals(0, leitor.getEmprestimos().size());
        assertFalse(leitor.removerEmprestimo(1));
    }

    @Test
    void testProtecaoDaColecao() {
        Leitor leitor = new Leitor("123", "Henrique", "henrique@gmail.com");
        Livro livro = new Livro("Dante Alighieri", "Inferno", 101);
        Emprestimo emp = new Emprestimo(1, leitor, livro, "17/08/2026");

        assertThrows(UnsupportedOperationException.class, () -> {
            leitor.getEmprestimos().add(emp);
        });
    }

    @Test
    void testComposicaoEsvaziamentoECalculoDelegado() {
        Livro livro = new Livro("Dante Alighieri", "Inferno", 101, 5);

        assertNotNull(livro.getDisponibilidade());
        assertEquals(5, livro.getDisponibilidade().getQuantidade());

        assertEquals(50.0, livro.calcularCustoManutencaoEstoque(10.0));

        livro.esvaziarEstoque();
        assertEquals(0, livro.getDisponibilidade().getQuantidade());
        assertFalse(livro.getDisponibilidade().isDisponivel());
    }
}