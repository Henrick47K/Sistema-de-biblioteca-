package biblioteca.dominio;

/**
 * Contrato que define os comportamentos essenciais de qualquer item pertencente
 * ao acervo da biblioteca.
 *
 * Toda classe que implementar esta interface deve garantir a consistência das operações
 * de movimentação do acervo (empréstimo, devolução e reserva) de acordo com suas regras
 * de negócio específicas.
 */
public interface ItemAcervo {

    /**
     * Realiza o empréstimo do item.
     *
     * @throws IllegalStateException se o item não puder ser emprestado (ex: já emprestado ou sem estoque).
     */
    void emprestar();

    /**
     * Realiza a devolução do item ao acervo.
     *
     * @throws IllegalStateException se o item já estiver disponível ou não constar como emprestado.
     */
    void devolver();

    /**
     * Solicita a reserva do item.
     *
     * As implementações devem definir se o item suporta reserva ou como a prioridade é tratada.
     * @throws IllegalStateException se o item estiver em um estado incompatível com reserva.
     */
    void reservar();

    /**
     * Obtém o título formatado e identificável do item do acervo.
     *
     * @return Nome/Título descritivo do item.
     */
    String getTitulo();

    /**
     * Verifica previamente se o item pode ser emprestado no momento,
     * permitindo validar o estado antes de realizar alterações.
     *
     * @return true se o item estiver disponível para empréstimo; false caso contrário.
     */
    boolean isDisponivel();
}
