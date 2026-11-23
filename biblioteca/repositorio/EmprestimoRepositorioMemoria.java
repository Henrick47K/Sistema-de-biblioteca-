package biblioteca.repositorio;

import biblioteca.dominio.RegistroEmprestimo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoRepositorioMemoria implements EmprestimoRepositorio {

    private final List<RegistroEmprestimo> registros = new ArrayList<>();

    @Override
    public void salvar(RegistroEmprestimo registro) {
        if (registro == null) {
            throw new IllegalArgumentException("Registro de empréstimo não pode ser nulo.");
        }
        registros.removeIf(r -> r.id() == registro.id());
        registros.add(registro);
    }

    @Override
    public List<RegistroEmprestimo> listarTodos() {
        return List.copyOf(registros);
    }

    @Override
    public Optional<RegistroEmprestimo> buscarPorId(int id) {
        for (RegistroEmprestimo r : registros) {
            if (r.id() == id) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }
}
