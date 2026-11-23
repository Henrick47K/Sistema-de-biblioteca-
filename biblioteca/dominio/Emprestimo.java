package biblioteca.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;

/**
 * Representa o vínculo de empréstimo entre um Leitor e um ItemAcervo,
 * controlando seu ciclo de vida (ATIVO -> FINALIZADO) e validando datas na origem.
 */
public class Emprestimo {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

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
        ValidadorDominio.exigirMaiorQueZero(id, "ID do empréstimo deve ser maior que zero.");
        ValidadorDominio.exigirNaoNulo(leitor, "Leitor não pode ser nulo.");
        ValidadorDominio.exigirNaoNulo(item, "Item do acervo não pode ser nulo.");
        ValidadorDominio.exigirTextoNaoVazio(dataEmp, "Data de empréstimo não pode ser nula ou vazia.");
        validarFormatoData(dataEmp);

        this.id = id;
        this.leitor = leitor;
        this.item = item;
        this.dataEmp = dataEmp;
        this.status = StatusEmprestimo.ATIVO;
    }

    private void validarFormatoData(String data) {
        try {
            LocalDate.parse(data, FORMATO_DATA);
        } catch (DateTimeParseException e) {
            throw new DadosInvalidosException(
                    "Data inválida ('" + data + "'): informe uma data no padrão dd/MM/aaaa (ex: 22/11/2026).");
        }
    }

    public void iniciarEmp() {
        if (this.status == StatusEmprestimo.FINALIZADO) {
            throw new IllegalStateException("Empréstimo finalizado não pode ser reiniciado.");
        }
        this.status = StatusEmprestimo.ATIVO;
        this.item.emprestar();
    }

    public void finalizarEmp(String dataDev) {
        if (this.status == StatusEmprestimo.FINALIZADO) {
            throw new IllegalStateException("Empréstimo já está finalizado.");
        }
        setDataDev(dataDev);
        this.status = StatusEmprestimo.FINALIZADO;
        this.item.devolver();
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
        ValidadorDominio.exigirMaiorQueZero(id, "ID do empréstimo deve ser maior que zero.");
        this.id = id;
    }

    public Leitor getLeitor() { return leitor; }
    public ItemAcervo getItem() { return item; }
    public String getDataEmp() { return dataEmp; }

    public void setDataEmp(String dataEmp) {
        ValidadorDominio.exigirTextoNaoVazio(dataEmp, "Data de empréstimo não pode ser nula ou vazia.");
        validarFormatoData(dataEmp);
        this.dataEmp = dataEmp;
    }

    public String getDataDev() { return dataDev; }

    public void setDataDev(String dataDev) {
        ValidadorDominio.exigirTextoNaoVazio(dataDev, "Data de devolução não pode ser nula ou vazia.");
        validarFormatoData(dataDev);
        this.dataDev = dataDev;
    }

    public StatusEmprestimo getStatus() { return status; }
}
