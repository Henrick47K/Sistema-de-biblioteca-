package biblioteca.dominio;

import biblioteca.repositorio.EmprestimoRepositorio;
import java.util.List;
import java.util.Optional;

public class BibliotecaService {

    private final EmprestimoRepositorio repositorio;

    public BibliotecaService(EmprestimoRepositorio repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("O repositório de empréstimos não pode ser nulo.");
        }
        this.repositorio = repositorio;
    }

    public Emprestimo realizarEmprestimo(int idEmprestimo, Leitor leitor, ItemAcervo item, String dataEmp) {
        if (leitor == null) {
            throw new IllegalArgumentException("Leitor não pode ser nulo.");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item do acervo não pode ser nulo.");
        }

        Emprestimo emprestimo = new Emprestimo(idEmprestimo, leitor, item, dataEmp);
        leitor.adicionarEmprestimo(emprestimo);

        try {
            emprestimo.iniciarEmp();
        } catch (RuntimeException e) {
            leitor.removerEmprestimo(idEmprestimo);
            throw e;
        }

        repositorio.salvar(converterParaRegistro(emprestimo));
        return emprestimo;
    }

    public void finalizarEmprestimo(Emprestimo emprestimo, String dataDev) {
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não pode ser nulo.");
        }
        emprestimo.finalizarEmp(dataDev);
        repositorio.salvar(converterParaRegistro(emprestimo));
    }

    public List<RegistroEmprestimo> listarHistoricoEmprestimos() {
        return repositorio.listarTodos();
    }

    public Optional<RegistroEmprestimo> buscarRegistroPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    private RegistroEmprestimo converterParaRegistro(Emprestimo emprestimo) {
        return new RegistroEmprestimo(
                emprestimo.getId(),
                emprestimo.getLeitor().getCpf(),
                emprestimo.getItem().getTitulo(),
                emprestimo.getDataEmp(),
                emprestimo.getDataDev(),
                emprestimo.getStatus().name()
        );
    }
}