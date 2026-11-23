package biblioteca.dominio;

public class LeitorEstudante extends Leitor {

    private String matricula;

    public LeitorEstudante(String cpf, String nome, String email, String matricula) {
        super(cpf, nome, email);
        ValidadorDominio.exigirTextoNaoVazio(matricula, "Matrícula não pode ser nula ou vazia.");
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        ValidadorDominio.exigirTextoNaoVazio(matricula, "Matrícula não pode ser nula ou vazia.");
        this.matricula = matricula;
    }
}
