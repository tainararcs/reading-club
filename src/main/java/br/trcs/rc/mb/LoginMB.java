package br.trcs.rc.mb;

import java.io.Serializable;

import br.trcs.rc.model.User;
import br.trcs.rc.utils.Consts;
import br.trcs.rc.utils.SecurityUtils;
import br.trcs.rc.dao.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Managed Bean responsável pela autenticação e controle de sessão do usuário.
 * O escopo {@link SessionScoped} garante que o usuário autenticado permaneça disponível durante toda a sessão.
 */
@Named(Consts.LOGIN_MB)
@SessionScoped
public class LoginMB implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * E-mail informado no formulário de login.
     */
    private String email;

    /**
     * Senha informada no formulário de login.
     */
    private String password;

    /**
     * Usuário autenticado e armazenado na sessão.
     */
    private User loggedUser;

    /**
     * Método executado após a criação do bean.
     * Pode ser utilizado para inicializações futuras.
     */
    @PostConstruct
    public void init() {}

    /**
     * Retorna o e-mail informado no login.
     *
     * @return e-mail do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail informado no login.
     *
     * @param email e-mail do usuário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a senha informada no login.
     *
     * @return senha do usuário.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Define a senha informada no login.
     *
     * @param password senha do usuário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna o usuário atualmente autenticado.
     *
     * @return usuário logado.
     */
    public User getLoggedUser() {
		return loggedUser;
	}

    /**
     * Define o usuário autenticado na sessão.
     *
     * @param loggedUser usuário logado.
     */
	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
     * Verifica se o usuário autenticado possui perfil de administrador.
     *
     * @return {@code true} se for ADMIN, {@code false} caso contrário.
     */
	public boolean isAdmin() {
	    return loggedUser != null && Consts.ADMIN.equalsIgnoreCase(loggedUser.getProfile());
	}
	
	/**
     * Verifica se o usuário autenticado possui perfil de usuário comum.
     *
     * @return {@code true} se for USER, {@code false} caso contrário.
     */
	public boolean isUser() {
	    return loggedUser != null && Consts.USER.equalsIgnoreCase(loggedUser.getProfile());
	}

	/**
	 * Realiza a autenticação do usuário.
	 *
	 * @return página de redirecionamento após login bem-sucedido.
	 */
    public String login() {    
        UserDAO dao = new UserDAO(User.class);
        User user = dao.findByEmailAndPassword(email, SecurityUtils.encryptPassword(password));
        
        if (user != null) {
        	// Verifica comfirmação de email.
        	if (!user.getConfirmedEmail()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Consts.ACTIVATED_ACCOUNT_ERROR, null));
                return null;
            }
        	
        	this.loggedUser = user; // Guarda na sessão.
        	
        	// Limpa o formulário.
        	email = null;
        	password = null;
        	
        	return "home?faces-redirect=true";
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Consts.CREDENTIALS_ERROR, null));
        return null; // Permanece na página.
    }

    /**
     * Realiza o logout do usuário.
     * Invalida a sessão atual e redireciona para a página de login.
     *
     * @return página de login.
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
}
