package br.trcs.rc.mb;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import br.trcs.rc.dao.DAO;
import br.trcs.rc.dao.UserDAO;
import br.trcs.rc.model.User;
import br.trcs.rc.service.EmailService;
import br.trcs.rc.utils.Consts;
import br.trcs.rc.utils.FacesMessages;
import br.trcs.rc.utils.MessagesConsts;
import br.trcs.rc.utils.SecurityUtils;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Managed Bean responsável pelo gerenciamento de usuários.
 * O escopo {@link ViewScoped} garante que os dados sejam mantidos enquanto o usuário interage com a mesma página.
 */
@Named(Consts.USER_MB)
@ViewScoped
public class UserMB implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * Perfil padrão atribuído aos usuários cadastrados.
     */
	private static final String PROFILE = "user";
	
	/**
	 * 
	 */
	@Inject
	private LoginMB loginMB;
	
	/**
     * Usuário atualmente manipulado no formulário.
     */
	private User user = new User();

	/**
     * Lista de todos os usuários cadastrados.
     */
	private List<User> usersList;

	/**
     * Lista de usuários disponíveis para novos empréstimos.
     */
	private List<User> availablesList;
	
	/**
	 * 
	 * @return
	 */
	private boolean isUserLogged() {
        return loginMB != null && loginMB.getLoggedUser() != null;
    }

	/**
     * Retorna o usuário em edição/cadastro.
     *
     * @return objeto {@link User}
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Retorna o perfil padrão atribuído aos usuários.
     *
     * @return perfil do usuário.
     */
    public static String getProfile() {
		return PROFILE;
	}

	/**
     * Retorna a lista de todos os usuários cadastrados no sistema.
     *
     * @return lista de usuários.
     */
	public List<User> getUsersList() {
    	if (usersList == null) {
            DAO<User> dao = new DAO<>(User.class);
            usersList = dao.list(); 
        }
		return usersList;
	}
    
	/**
     * Retorna a lista de usuários disponíveis para empréstimos.
     * Um usuário é considerado disponível quando não possui empréstimos ativos.
     *
     * @return lista de usuários disponíveis.
     */
	public List<User> getAvailablesList() {
		if (availablesList == null) {
            UserDAO dao = new UserDAO(User.class);
            availablesList = dao.listAvailableUsers(); 
        }
		return availablesList;
	}

	/**
     * Realiza o cadastro de um novo usuário.
     * 
     * @return página de redirecionamento após o cadastro.
     */
	public String insert() {
		// Interrompe a execução do método.
		if (!checkUserValues()) return null;
		
        try {
        	// Remove formatação do CPF antes de salvar.
        	String cpfClean = user.getCpf().replace(".", "").replace("-", "");
        	user.setCpf(cpfClean);
        	user.setProfile(PROFILE);
        	
        	user.setConfirmedEmail(false); 
        	user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        	
        	DAO<User> dao = new DAO<User>(User.class);
            dao.insert(user);
            
            // Envia e-mail de confirmação de cadastro com link de ativação.
            EmailService.sendActivationEmail(user.getEmail(), user.getName(), user.getCpf());
            FacesMessages.addInfoMessage(MessagesConsts.CREDENTIALS_ERROR);
            
            user = new User(); // Limpa o formulário.
        } catch (Exception e) {
        	if (e.getMessage().contains("duplicate key"))
        		FacesMessages.addErrorMessage(MessagesConsts.ALREADY_USER_ERROR);
        	else 
        		FacesMessages.addErrorMessage(e.getMessage());
        	return null;
        }
        
        return (isUserLogged()) ? Consts.ADD_USER_PAGE : "login?faces-redirect=true";
    }
	
	private boolean checkUserValues() {
		if (!loginMB.isAdmin()) { 
	        try {
	            FacesContext.getCurrentInstance().getExternalContext().redirect(Consts.ACCESS_DENIED_HTML);
	        } catch (Exception ignored) {}
	        return false;
		}	
		
		if (hasEmptyRequiredFields()) {
			FacesMessages.addErrorMessage(MessagesConsts.EMPTY_FIELDS_ERROR);
	    	return false;
		}
		
		if (user.getBirthDate().isAfter(LocalDate.now())) {
			FacesMessages.addErrorMessage(MessagesConsts.LATER_DATA_ERROR);
		    return false;
		}
		
		return true;
	}
	
	private boolean hasEmptyRequiredFields() {
	    return isBlank(user.getCpf()) || isBlank(user.getName()) || isBlank(user.getLogin()) || isBlank(user.getPassword()) || isBlank(user.getEmail());
	}
	
	private boolean isBlank(String value) {
	    return value == null || value.isBlank();
	}
	
}
