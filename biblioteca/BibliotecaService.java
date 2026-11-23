package biblioteca.servico;

import biblioteca.dominio.*;
import biblioteca.repositorio.EmprestimoRepositorio;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço responsável por orquestrar os casos de uso da biblioteca.
 * Liga o domínio ao repositório garantindo que o estado permaneça consistente
 * mesmo quando ocorrem falhas de negócio ou erros técnicos.
 */
public class BibliotecaService {

    private final EmprestimoRepositorio repositorio;

    // Recebe a interface do repositório por injeção de dependência no construtor
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

        // 1. VALIDAÇÃO ANTES DE ALTERAR ESTADO (Requisito 4):
        // Se qualquer regra falhar aqui, nenhum objeto em memória ou arquivo é modificado.
        if (repositorio.buscarPorId(idEmprestimo).isPresent()) {
            throw new DadosInvalidosException(
                    "Já existe um empréstimo registrado com o ID " + idEmprestimo
                            + ". Informe um número de empréstimo diferente.");
        }
        if (!leitor.podeRealizarNovoEmprestimo()) {
            throw new LimiteEmprestimosExcedidoException(leitor.getNome(), leitor.getLimiteEmprestimos());
        }
        if (!item.isDisponivel()) {
            throw new ItemIndisponivelException(item.getTitulo());
        }

        // Cria o empréstimo (também valida o formato da data na origem)
        Emprestimo emprestimo = new Emprestimo(idEmprestimo, leitor, item, dataEmp);

        // 2. EXECUÇÃO ATÔMICA COM ROLLBACK:
        // Se falhar ao salvar no arquivo, desfaz as alterações feitas na memória.
        leitor.adicionarEmprestimo(emprestimo);
        boolean itemEmprestado = false;
        try {
            emprestimo.iniciarEmp();
            itemEmprestado = true;
            repositorio.salvar(converterParaRegistro(emprestimo));
        } catch (RuntimeException e) {
            if (itemEmprestado) {
                item.devolver();
            }
            leitor.removerEmprestimo(idEmprestimo);
            throw e;
        }

        return emprestimo;
    }

    public void finalizarEmprestimo(Emprestimo emprestimo, String dataDev) {
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não pode ser nulo.");
        }
        emprestimo.finalizarEmp(dataDev);
        repositorio.salvar(converterParaRegistro(emprestimo));
    }

    public void reservarLivro(Leitor leitor, Livro livro) {
        if (leitor == null) {
            throw new IllegalArgumentException("Leitor não pode ser nulo.");
        }
        if (livro == null) {
            throw new IllegalArgumentException("Livro não pode ser nulo.");
        }
        leitor.reservarLivro(livro);
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