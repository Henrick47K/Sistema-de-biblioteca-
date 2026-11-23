package biblioteca.repositorio;

import biblioteca.dominio.RegistroEmprestimo;
import java.util.List;
import java.util.Optional;

public interface EmprestimoRepositorio {

    void salvar(RegistroEmprestimo registro);

    List<RegistroEmprestimo> listarTodos();

    Optional<RegistroEmprestimo> buscarPorId(int id);
}
