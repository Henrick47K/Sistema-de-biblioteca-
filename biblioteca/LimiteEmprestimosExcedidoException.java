package biblioteca.dominio;

public class LimiteEmprestimosExcedidoException extends BibliotecaException {
    public LimiteEmprestimosExcedidoException(String nomeLeitor, int limite) {
        super("Não foi possível realizar o empréstimo para " + nomeLeitor
                + ": limite máximo de " + limite
                + " empréstimo(s) ativo(s) atingido. Devolva um item antes de pegar outro.");
    }
}