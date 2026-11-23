package biblioteca.dominio;

public record RegistroEmprestimo(
        int id,
        String cpfLeitor,
        String tituloItem,
        String dataEmp,
        String dataDev,
        String status
) {
    public RegistroEmprestimo {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do registro deve ser maior que zero.");
        }
        if (cpfLeitor == null || cpfLeitor.isBlank()) {
            throw new IllegalArgumentException("CPF do leitor não pode ser nulo ou vazio.");
        }
        if (tituloItem == null || tituloItem.isBlank()) {
            throw new IllegalArgumentException("Título do item não pode ser nulo ou vazio.");
        }
        if (dataEmp == null || dataEmp.isBlank()) {
            throw new IllegalArgumentException("Data de empréstimo não pode ser nula ou vazia.");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio.");
        }
    }
}
