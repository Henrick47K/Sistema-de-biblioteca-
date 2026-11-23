package biblioteca.dominio;

public class ItemIndisponivelException extends BibliotecaException {
    public ItemIndisponivelException(String tituloItem) {
        super("Não foi possível concluir a operação para '" + tituloItem
                + "': o item está emprestado ou sem exemplares disponíveis. "
                + "Escolha outro item ou aguarde a devolução.");
    }
}