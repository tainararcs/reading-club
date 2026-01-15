package br.trcs.rc.utils;

/**
 * Classe utilitária que centraliza constantes globais da aplicação.
 * Esta classe não deve ser instanciada.
 */
public class Consts {

	/**
     * Construtor privado para impedir a instanciação da classe.
     */ 
	private Consts() {}

	/**
     * Nome do Managed Bean responsável pela autenticação e controle de sessão do usuário.
     */
	public static final String LOGIN_MB = "loginMB";

	/**
     * Nome do Managed Bean responsável pelo gerenciamento de usuários.
     */
	public static final String USER_MB = "userMB";

	/**
     * Nome do Managed Bean responsável pelo gerenciamento de caixas.
     */
	public static final String BOX_MB = "boxMB";

	/**
     * Nome do Managed Bean responsável pelo gerenciamento de revistas.
     */
	public static final String COMIC_MB = "comicMB";

	/**
     * Nome do Managed Bean responsável pelo gerenciamento de empréstimos.
     */
	public static final String BORROWING_MB = "borrowingMB";

	/**
     * Nome do Managed Bean responsável pela geração de relatórios.
     */
	public static final String REPORT_MB = "reportMB";

	/**
     * Nome do Managed Bean responsável pela ativação de contas de usuários.
     */
	public static final String ACTIVE_MB = "activateMB";
}
