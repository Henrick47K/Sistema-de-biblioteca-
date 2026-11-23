package biblioteca.dominio;

/**
 * Utilitário interno do domínio para centralizar validações básicas de invariantes,
 * eliminando duplicação de verificações de nulidade, texto vazio e valores positivos.
 */
final class ValidadorDominio {

    private ValidadorDominio() {
    }

    static void exigirNaoNulo(Object valor, String mensagem) {
        if (valor == null) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    static void exigirTextoNaoVazio(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    static void exigirMaiorQueZero(int valor, String mensagem) {
        if (valor <= 0) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    static void exigirNaoNegativo(int valor, String mensagem) {
        if (valor < 0) {
            throw new IllegalArgumentException(mensagem);
        }
    }
}
