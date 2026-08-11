package biblioteca;

public class Emprestimo {

    private int id;
    private String dataEmp;
    private String dataDev;

    public Emprestimo(int id, String dataEmp) {
        this.id = id;
        this.dataEmp = dataEmp;
    }

    void iniciarEmp() {
        System.out.println("Empréstimo iniciado!");
    }

    void finalizarEmp() {
        System.out.println("Empréstimo finalizado!");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataEmp() {
        return dataEmp;
    }

    public void setDataEmp(String dataEmp) {
        this.dataEmp = dataEmp;
    }

    public String getDataDev() {
        return dataDev;
    }

    public void setDataDev(String dataDev) {
        this.dataDev = dataDev;
    }
}