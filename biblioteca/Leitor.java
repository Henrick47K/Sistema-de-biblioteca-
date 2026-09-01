package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leitor {

    private String cpf;
    private String nome;
    private String email;
    // 1. Coleção adicionada para representar a associação 1:N
    private List<Emprestimo> emprestimos;

    public Leitor(String cpf, String nome, String email) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail não pode ser nulo ou vazio.");
        }

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        // Inicialização da lista no construtor para evitar NullPointerException
        this.emprestimos = new ArrayList<>();
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não pode ser nulo.");
        }
        this.emprestimos.add(emprestimo);
    }

    public List<Emprestimo> getEmprestimos() {
        return Collections.unmodifiableList(emprestimos);
    }

    public void cadastroLeitor() {
        System.out.println("Leitor " + nome + " cadastrado com sucesso!");
    }

    public void reservarLivro(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("Livro não pode ser nulo.");
        }
        livro.reservar();
        System.out.println("Livro '" + livro.getTitulo() + "' reservado para o leitor " + nome + "!");
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio.");
        }
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail não pode ser nulo ou vazio.");
        }
        this.email = email;
    }
}