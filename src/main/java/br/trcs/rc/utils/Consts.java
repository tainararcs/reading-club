package br.trcs.rc.utils;

/**
 * Classe utilitária que centraliza constantes globais da aplicação.
 */
public class Consts {
	/**
     * Construtor privado para impedir a instanciação da classe.
     */ 
	private Consts() {}
	
	/**
	 * Nome do projeto.
	 */
	public static final String READING_CLUB = "Reading Club";
	
	/**
	 * Nome padrão do usuário comum do sistema.
	 */
	public static final String USER = "user";
	
	/**
	 * Nome padrão do usuário administrador do sistema.
	 */
	public static final String ADMIN = "admin";
	
	/**
	 * Nome da tabela onde são armazenados os usuários.
	 */
	public static final String USER_TABLE = "users";
	
	/**
	 * Nome da tabela onde são armazenados os usuários.
	 */
	public static final String COMIC_TABLE = "comics";
	
	/**
	 * Nome da tabela onde são armazenados os usuários.
	 */
	public static final String BOX_TABLE = "boxes";
	
	/**
	 * Nome da tabela onde são armazenados os usuários.
	 */
	public static final String BORROWING_TABLE = "borrowings";

	/**
     * Nome do Managed Bean responsável pela exposição das constantes necessárias às páginas xhtml.
     */
	public static final String APP_MB = "appMB";
	
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
	
	/**
	 *  Títulos das páginas xhtml ou conteúdo dos botões/links.
	 */
	public static final String LOGIN = "Log in";
	public static final String LOGOUT = "Log out";
	public static final String HOME = "Home";
	public static final String ADD_USERS = "Cadastrar Usuários";
	public static final String REGISTER_USER = "Registrar Usuário";
	public static final String ADD_COMICS = "Cadastrar Revistas";
	public static final String REGISTER_COMIC = "Registrar Revista";
	public static final String LIST_COMICS = "Listar Revistas";
	public static final String ADD_BOXES = "Cadastrar Caixas";
	public static final String REGISTER_BOX = "Registrar Caixa";
	public static final String ADD_BORROWINGS = "Cadastrar Empréstimos";
	public static final String REGISTER_BORROWING = "Registrar Empréstimo";
	public static final String BORROWINGS_HISTORY = "Histórico de Empréstimos";
	public static final String DO_BORROW = "Solicitar empréstimo";
	public static final String GENERATE_REPORT = "Gerar relatório";
    
    /**
	 *  Páginas principais.
	 */
	public static final String LOGIN_PAGE = "login";
	public static final String HOME_PAGE = "home";
	public static final String ADD_USER_PAGE = "adduser";
	public static final String ADD_COMIC_PAGE = "addcomic";
	public static final String ADD_BOX_PAGE = "addbox";
	public static final String ADD_BORROW_PAGE = "addborrowing";
	
	/**
	 *  Arquivos principais.
	 */
	public static final String LOGIN_JS = "login.js";
	public static final String MESSAGES_JS = "messages.js";
	public static final String MENU_HTML = "menu.xhtml";
	public static final String HEADER_HTML = "header.xhtml";
	public static final String ACCESS_DENIED_HTML = "accessdenied.xhtml";
}
