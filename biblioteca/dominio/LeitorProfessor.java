package biblioteca.dominio;

public class LeitorProfessor extends Leitor {

    private String departamento;

    public LeitorProfessor(String cpf, String nome, String email, String departamento) {
        super(cpf, nome, email);
        ValidadorDominio.exigirTextoNaoVazio(departamento, "Departamento não pode ser nulo ou vazio.");
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        ValidadorDominio.exigirTextoNaoVazio(departamento, "Departamento não pode ser nulo ou vazio.");
        this.departamento = departamento;
    }
}
