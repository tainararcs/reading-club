package br.trcs.rc.utils;

/**
 * Classe utilitária responsável por funcionalidades relacionadas à segurança da aplicação.
 */
public class SecurityUtils {

    /**
     * Criptografa (de forma simples) a senha informada. Utiliza o {@link String#hashCode()}.
     *
     * @param password senha original digitada pelo usuário.
     * @return hash da senha em formato {@link String}.
     */
    public static String encryptPassword(String password) {
        return String.valueOf(password.hashCode());
    }
}