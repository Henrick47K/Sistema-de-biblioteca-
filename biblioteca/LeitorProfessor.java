package biblioteca;

public class LeitorProfessor extends Leitor {

    private String departamento;

    public LeitorProfessor(String cpf, String nome, String email, String departamento) {
        super(cpf, nome, email);
        if (departamento == null || departamento.isBlank()) {
            throw new IllegalArgumentException("Departamento não pode ser nulo ou vazio.");
        }
        this.departamento = departamento;
    }

    @Override
    public int getLimiteEmprestimos() {
        return 10;
    }

    @Override
    public int getPrazoDevolucaoDias() {
        return 30;
    }

    @Override
    public void cadastroLeitor() {
        System.out.println("Leitor Professor '" + getNome() + "' (Dept: " + departamento + ") cadastrado com sucesso!");
    }

    @Override
    public void reservarLivro(Livro livro) {
        super.reservarLivro(livro);
        System.out.println("-> Prioridade docente concedida (prazo estendido para 30 dias).");
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        if (departamento == null || departamento.isBlank()) {
            throw new IllegalArgumentException("Departamento não pode ser nulo ou vazio.");
        }
        this.departamento = departamento;
    }
}