package biblioteca.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe base abstrata que representa um leitor da biblioteca.
 * Concentra validações de entrada na origem e o controle de empréstimos ativos.
 */
public abstract class Leitor {

    private String cpf;
    private String nome;
    private String email;
    private List<Emprestimo> emprestimos;

    public Leitor(String cpf, String nome, String email) {
        ValidadorDominio.exigirTextoNaoVazio(cpf, "CPF não pode ser nulo ou vazio.");
        ValidadorDominio.exigirTextoNaoVazio(nome, "Nome não pode ser nulo ou vazio.");
        ValidadorDominio.exigirTextoNaoVazio(email, "E-mail não pode ser nulo ou vazio.");
        validarFormatoEmail(email);

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.emprestimos = new ArrayList<>();
    }

    private void validarFormatoEmail(String email) {
        int arroba = email.indexOf('@');
        if (arroba <= 0
                || arroba >= email.length() - 1
                || email.indexOf('@', arroba + 1) != -1
                || !email.substring(arroba + 1).contains(".")
                || email.startsWith(".", arroba + 1)
                || email.endsWith(".")) {
            throw new DadosInvalidosException(
                    "E-mail inválido ('" + email + "'): use o formato usuario@dominio.com e tente novamente.");
        }
    }

    public abstract int getLimiteEmprestimos();

    public abstract int getPrazoDevolucaoDias();

    // Conta apenas os empréstimos com status ATIVO (devolvidos não bloqueiam a cota do leitor)
    public long getQuantidadeEmprestimosAtivos() {
        return this.emprestimos.stream()
                .filter(e -> e.getStatus() == Emprestimo.StatusEmprestimo.ATIVO)
                .count();
    }

    // Verifica previamente se o leitor ainda possui cota livre antes de alterar o estado
    public boolean podeRealizarNovoEmprestimo() {
        return getQuantidadeEmprestimosAtivos() < getLimiteEmprestimos();
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        ValidadorDominio.exigirNaoNulo(emprestimo, "Empréstimo não pode ser nulo.");
        if (this.emprestimos.contains(emprestimo)) {
            throw new IllegalStateException("Este empréstimo já está cadastrado para o leitor.");
        }
        if (!podeRealizarNovoEmprestimo()) {
            throw new IllegalStateException("Limite de empréstimos atingido para este leitor.");
        }
        this.emprestimos.add(emprestimo);
    }

    public Emprestimo buscarEmprestimoPorId(int id) {
        for (Emprestimo e : emprestimos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public boolean removerEmprestimo(int id) {
        Emprestimo emp = buscarEmprestimoPorId(id);
        if (emp != null) {
            return this.emprestimos.remove(emp);
        }
        return false;
    }

    public List<Emprestimo> getEmprestimos() {
        return List.copyOf(emprestimos);
    }

    public void reservarLivro(Livro livro) {
        ValidadorDominio.exigirNaoNulo(livro, "Livro não pode ser nulo.");
        livro.reservar();
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        ValidadorDominio.exigirTextoNaoVazio(cpf, "CPF não pode ser nulo ou vazio.");
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        ValidadorDominio.exigirTextoNaoVazio(nome, "Nome não pode ser nulo ou vazio.");
        this.nome = nome;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        ValidadorDominio.exigirTextoNaoVazio(email, "E-mail não pode ser nulo ou vazio.");
        validarFormatoEmail(email);
        this.email = email;
    }
}
