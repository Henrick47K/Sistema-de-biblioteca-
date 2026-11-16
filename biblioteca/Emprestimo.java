package biblioteca.dominio;

import java.util.Objects;

public class Emprestimo {

    private int id;
    private Leitor leitor;
    private ItemAcervo item;
    private String dataEmp;
    private String dataDev;
    private StatusEmprestimo status;

    public enum StatusEmprestimo {
        ATIVO,
        FINALIZADO
    }

    public Emprestimo(int id, Leitor leitor, ItemAcervo item, String dataEmp) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do empréstimo deve ser maior que zero.");
        }
        if (leitor == null) {
            throw new IllegalArgumentException("Leitor não pode ser nulo.");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item do acervo não pode ser nulo.");
        }
        if (dataEmp == null || dataEmp.isBlank()) {
            throw new IllegalArgumentException("Data de empréstimo não pode ser nula ou vazia.");
        }

        this.id = id;
        this.leitor = leitor;
        this.item = item;
        this.dataEmp = dataEmp;
        this.status = StatusEmprestimo.ATIVO;
    }

    public void iniciarEmp() {
        if (this.status == StatusEmprestimo.FINALIZADO) {
            throw new IllegalStateException("Empréstimo finalizado não pode ser reiniciado.");
        }
        this.status = StatusEmprestimo.ATIVO;
        this.item.emprestar();
        System.out.println("Empréstimo " + id + " iniciado para " + leitor.getNome() + "!");
    }

    public void finalizarEmp(String dataDev) {
        if (this.status == StatusEmprestimo.FINALIZADO) {
            throw new IllegalStateException("Empréstimo já está finalizado.");
        }
        setDataDev(dataDev);
        this.status = StatusEmprestimo.FINALIZADO;
        this.item.devolver();
        System.out.println("Empréstimo " + id + " finalizado!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do empréstimo deve ser maior que zero.");
        }
        this.id = id;
    }

    public Leitor getLeitor() { return leitor; }
    public ItemAcervo getItem() { return item; }
    public String getDataEmp() { return dataEmp; }

    public void setDataEmp(String dataEmp) {
        if (dataEmp == null || dataEmp.isBlank()) {
            throw new IllegalArgumentException("Data de empréstimo não pode ser nula ou vazia.");
        }
        this.dataEmp = dataEmp;
    }

    public String getDataDev() { return dataDev; }

    public void setDataDev(String dataDev) {
        if (dataDev == null || dataDev.isBlank()) {
            throw new IllegalArgumentException("Data de devolução não pode ser nula ou vazia.");
        }
        this.dataDev = dataDev;
    }

    public StatusEmprestimo getStatus() { return status; }
}