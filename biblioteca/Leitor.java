package biblioteca;

public class Leitor {

    private String cpf;
    private String nome;
    private String email;

    public Leitor(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    void cadastroLeitor() {
        System.out.println("Leitor cadastrado!");
    }

    void reservarLivro() {
        System.out.println("Livro reservado!");
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}