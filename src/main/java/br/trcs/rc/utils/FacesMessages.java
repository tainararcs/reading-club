package br.trcs.rc.utils;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 * Classe utilitária para criação e exibição de mensagens na interface
 * utilizando o sistema de mensagens do Jakarta Faces.
 */
public class FacesMessages {

    /**
     * Construtor privado para impedir a instanciação da classe.
     */
    private FacesMessages() {}

    /**
     * Adiciona uma mensagem de erro ao contexto atual do Jakarta Faces.
     *
     * @param message conteúdo da mensagem.
     */
    public static void addErrorMessage(String message) {
    	addMessage(FacesMessage.SEVERITY_ERROR, message);
	}

    /**
     * Adiciona uma mensagem de informação ao contexto atual do Jakarta Faces.
     *
     * @param message conteúdo da mensagem.
     */
    public static void addInfoMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message);
    }

    /**
     * Adiciona uma mensagem de aviso ao contexto atual do Jakarta Faces.
     *
     * @param message conteúdo da mensagem.
     */
    public static void addWarnMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, message);
    }

    /**
     * Adiciona uma mensagem ao contexto atual do Jakarta Faces.
     *
     * @param severity nível de severidade da mensagem.
     * @param message conteúdo da mensagem.
     */
    private static void addMessage(FacesMessage.Severity severity, String message) {
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, null, message));
    }
}
