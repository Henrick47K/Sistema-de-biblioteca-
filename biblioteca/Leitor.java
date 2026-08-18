package biblioteca;

public class Leitor {

    private String cpf;
    private String nome;
    private String email;

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
    }

    public void cadastroLeitor() {
        System.out.println("Leitor " + nome + " cadastrado com sucesso!");
    }

    public void reservarLivro(Livro livro) {
        livro.reservar();
        System.out.println("Livro '" + livro.getTitulo() + "' reservado para o leitor " + nome + "!");
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio.");
        }
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail não pode ser nulo ou vazio.");
        }
        this.email = email;
    }
}