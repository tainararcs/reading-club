package br.trcs.rc.utils;

public class MessagesConsts {
	
	/** */
	private MessagesConsts() {}
	
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
	public static final String ALREADY_COMIC_ERROR = "Revista já cadastrada";
    public static final String ADD_BOX_ERROR = "Erro ao registrar caixa";
    public static final String ALREADY_BOX_ERROR = "Caixa já cadastrada";
    public static final String ADD_BORROW_ERROR = "Erro ao registrar empréstimo";
    public static final String ALREADY_BORROW_ERROR = "Você já possui um empréstimo em aberto";
    public static final String EMPTY_FIELDS_ERROR = "Preencha todos os campos obrigatórios";
}
