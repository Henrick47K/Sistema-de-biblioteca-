package biblioteca;

import biblioteca.dominio.*;
import biblioteca.repositorio.EmprestimoRepositorio;
import biblioteca.repositorio.EmprestimoRepositorioArquivo;
import biblioteca.repositorio.EmprestimoRepositorioMemoria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaPersistenciaTest {

    @Test
    void testServicoComRepositorioEmMemoria() {
        // Arrange
        EmprestimoRepositorio repositorio = new EmprestimoRepositorioMemoria();
        BibliotecaService service = new BibliotecaService(repositorio);
        Leitor estudante = new LeitorEstudante("111.222.333-44", "Ana Paula", "ana@email.com", "2026100");
        Livro livro = new Livro("Martin Fowler", "Refactoring", 201, 2);

        // Act
        service.realizarEmprestimo(1, estudante, livro, "16/11/2026");

        // Assert
        List<RegistroEmprestimo> historico = service.listarHistoricoEmprestimos();
        assertEquals(1, historico.size());

        Optional<RegistroEmprestimo> registroSalvo = service.buscarRegistroPorId(1);
        assertTrue(registroSalvo.isPresent());
        assertEquals("111.222.333-44", registroSalvo.get().cpfLeitor());
        assertEquals("Refactoring", registroSalvo.get().tituloItem());
        assertEquals("ATIVO", registroSalvo.get().status());
        assertNull(registroSalvo.get().dataDev());
    }

    @Test
    void testPersistenciaEmArquivoSobreviveANovaInstancia(@TempDir Path tempDir) {
        // Arrange
        Path arquivoCsv = tempDir.resolve("emprestimos_teste.csv");
        EmprestimoRepositorio repoInstancia1 = new EmprestimoRepositorioArquivo(arquivoCsv);
        RegistroEmprestimo registroOriginal = new RegistroEmprestimo(
                10,
                "999.888.777-66",
                "Clean Architecture",
                "10/11/2026",
                null,
                "ATIVO"
        );

        // Act: salva na primeira instância e lê a partir de uma nova instância
        repoInstancia1.salvar(registroOriginal);
        EmprestimoRepositorio repoInstancia2 = new EmprestimoRepositorioArquivo(arquivoCsv);
        List<RegistroEmprestimo> registrosRecuperados = repoInstancia2.listarTodos();

        // Assert
        assertEquals(1, registrosRecuperados.size());
        assertEquals(registroOriginal, registrosRecuperados.get(0));
    }

    @Test
    void testFinalizacaoAtualizaRegistroExistenteSemDuplicar() {
        // Arrange
        EmprestimoRepositorio repositorio = new EmprestimoRepositorioMemoria();
        BibliotecaService service = new BibliotecaService(repositorio);
        Leitor professor = new LeitorProfessor("555.444.333-22", "Prof. Carlos", "carlos@email.com", "Sistemas");
        ItemAcervo revista = new Revista("IEEE Software", 88);
        Emprestimo emprestimo = service.realizarEmprestimo(5, professor, revista, "01/11/2026");

        // Act
        service.finalizarEmprestimo(emprestimo, "16/11/2026");

        // Assert
        assertEquals(1, service.listarHistoricoEmprestimos().size());
        RegistroEmprestimo atualizado = service.buscarRegistroPorId(5).orElseThrow();
        assertEquals("FINALIZADO", atualizado.status());
        assertEquals("16/11/2026", atualizado.dataDev());
    }

    @Test
    void testErroDeArquivoRelancaUncheckedIOException(@TempDir Path tempDir) {
        // Arrange: aponta o repositório para um diretório em vez de um arquivo para forçar IOException
        EmprestimoRepositorio repoInvalido = new EmprestimoRepositorioArquivo(tempDir);
        RegistroEmprestimo registro = new RegistroEmprestimo(
                1, "123.456.789-00", "Domain-Driven Design", "16/11/2026", null, "ATIVO"
        );

        // Act & Assert
        assertThrows(UncheckedIOException.class, () -> repoInvalido.salvar(registro));
        assertThrows(UncheckedIOException.class, repoInvalido::listarTodos);
    }

    @Test
    void testFalhaNoEmprestimoNaoSalvaNoRepositorioNemNoLeitor() {
        // Arrange: livro com estoque zerado
        EmprestimoRepositorio repositorio = new EmprestimoRepositorioMemoria();
        BibliotecaService service = new BibliotecaService(repositorio);
        Leitor estudante = new LeitorEstudante("123.123.123-12", "Bruno", "bruno@email.com", "2026555");
        Livro livroSemEstoque = new Livro("Joshua Bloch", "Effective Java", 305, 0);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            service.realizarEmprestimo(99, estudante, livroSemEstoque, "16/11/2026");
        });

        assertEquals(0, estudante.getEmprestimos().size());
        assertTrue(service.listarHistoricoEmprestimos().isEmpty());
    }
}
