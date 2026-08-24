package biblioteca;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaTest {

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
}