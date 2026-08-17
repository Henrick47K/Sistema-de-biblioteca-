package biblioteca;

public class Emprestimo {

    private int id;
    private String dataEmp;
    private String dataDev;
    private StatusEmprestimo status;

    // Enum 2: Estado do empréstimo
    public enum StatusEmprestimo {
        ATIVO,
        FINALIZADO
    }

    public Emprestimo(int id, String dataEmp) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do empréstimo deve ser maior que zero.");
        }
        if (dataEmp == null || dataEmp.isBlank()) {
            throw new IllegalArgumentException("Data de empréstimo não pode ser nula ou vazia.");
        }

        this.id = id;
        this.dataEmp = dataEmp;
        this.status = StatusEmprestimo.ATIVO;
    }

    void iniciarEmp() {
        this.status = StatusEmprestimo.ATIVO;
        System.out.println("Empréstimo " + id + " iniciado!");
    }

    void finalizarEmp() {
        this.status = StatusEmprestimo.FINALIZADO;
        System.out.println("Empréstimo " + id + " finalizado!");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do empréstimo deve ser maior que zero.");
        }
        this.id = id;
    }

    public String getDataEmp() {
        return dataEmp;
    }

    public void setDataEmp(String dataEmp) {
        if (dataEmp == null || dataEmp.isBlank()) {
            throw new IllegalArgumentException("Data de empréstimo não pode ser nula ou vazia.");
        }
        this.dataEmp = dataEmp;
    }

    public String getDataDev() {
        return dataDev;
    }

    public void setDataDev(String dataDev) {
        this.dataDev = dataDev;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }
}