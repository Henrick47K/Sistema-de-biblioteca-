package biblioteca;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaTest {

    @Test
    void testConstrutoresInvalidosLancamExcecao() {
        // Valida se o construtor de Leitor rejeita valores inválidos
        assertThrows(IllegalArgumentException.class, () -> {
            new Leitor("", "Henrique", "henrique@gmail.com");
        });

        // Valida se o construtor de Livro rejeita códigos negativos ou zero
        assertThrows(IllegalArgumentException.class, () -> {
            new Livro("Autor", "Título", 0);
        });

        // Valida se Disponibilidade não aceita valores negativos
        assertThrows(IllegalArgumentException.class, () -> {
            new Disponibilidade(-5);
        });
    }

    @Test
    void testAlteracaoDeEstadoDoLivro() {
        Livro livro = new Livro("J.R.R. Tolkien", "O Hobbit", 101);

        assertEquals(Livro.StatusLivro.DISPONIVEL, livro.getStatus());

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

        Emprestimo emp = new Emprestimo(1, "17/08/2026");
        assertEquals(Emprestimo.StatusEmprestimo.ATIVO, emp.getStatus());

        emp.finalizarEmp();
        assertEquals(Emprestimo.StatusEmprestimo.FINALIZADO, emp.getStatus());
    }
}