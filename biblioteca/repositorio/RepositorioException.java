package biblioteca.repositorio;

/**
 * Exceção técnica da camada de persistência.
 * Isola falhas de leitura/escrita em arquivo (IOException) e dados corrompidos
 * para que a camada de domínio não dependa de detalhes técnicos de armazenamento.
 */
public class RepositorioException extends RuntimeException {

    public RepositorioException(String message) {
        super(message);
    }

    public RepositorioException(String message, Throwable cause) {
        // Preserva a exceção original (causa raiz) para rastreabilidade
        super(message, cause);
    }
}
