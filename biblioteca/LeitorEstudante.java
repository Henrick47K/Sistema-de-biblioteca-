package biblioteca;

public class LeitorEstudante extends Leitor {

    private String matricula;

    public LeitorEstudante(String cpf, String nome, String email, String matricula) {
        super(cpf, nome, email);
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Matrícula não pode ser nula ou vazia.");
        }
        this.matricula = matricula;
    }

    @Override
    public int getLimiteEmprestimos() {
        return 3;
    }

    @Override
    public int getPrazoDevolucaoDias() {
        return 14;
    }

    @Override
    public void cadastroLeitor() {
        System.out.println("Leitor Estudante '" + getNome() + "' (Matrícula: " + matricula + ") cadastrado com sucesso!");
    }

    @Override
    public void reservarLivro(Livro livro) {
        super.reservarLivro(livro);
        System.out.println("-> Cota especial de estudante aplicada (prazo de reserva estendido para 14 dias).");
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Matrícula não pode ser nula ou vazia.");
        }
        this.matricula = matricula;
    }
}