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
	
	// Mensagens de confirmação.
    public static final String ADD_SUCCESS = "Cadastro realizado";
    public static final String ADD_COMIC_SUCCESS = "Revista cadastrada";
    public static final String ADD_BOX_SUCCESS = "Caixa cadastrada";
    public static final String ADD_BORROW_SUCCESS = "Empréstimo cadastrado";
    public static final String RETURN_COMIC_SUCCESS = "Devolução registrada";
    
	// Mensagens de erro.
	public static final String CREDENTIALS_ERROR = "Email ou senha incorretos";
	public static final String ACTIVATED_ACCOUNT_ERROR = "Sua conta ainda não foi ativada";
	public static final String EXISTS_ACCOUNT_ERROR = "Conta já existente";
	public static final String ADD_USER_ERROR = "Erro ao registrar usuário";
	public static final String ALREADY_USER_ERROR = "Usuário já cadastrado";
	public static final String CONFIRMED_ERROR = "Verifique seu e-mail para ativar a conta";
	public static final String LATER_DATA_ERROR = "A data de nascimento não pode ser maior que hoje";
	public static final String ADD_COMIC_ERROR = "Erro ao registrar revista";
	public static final String UNAVAIABLE_COMIC_ERROR = "Revista indisponível";
	public static final String RETURN_COMIC_ERROR = "Erro ao registrar devolução";
	public static final String SELECT_COMIC_ERROR = "Selecione uma revista";
    public static final String ADD_BOX_ERROR = "Erro ao registrar caixa";
    public static final String ALREADY_BOX_ERROR = "Caixa já cadastrada";
    public static final String ADD_BORROW_ERROR = "Erro ao registrar empréstimo";
    public static final String ALREADY_BORROW_ERROR = "Você já possui um empréstimo em aberto";
    
}
