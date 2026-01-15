package br.trcs.rc.mb;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import br.trcs.rc.dao.DAO;
import br.trcs.rc.dao.UserDAO;
import br.trcs.rc.model.User;
import br.trcs.rc.service.EmailService;
import br.trcs.rc.utils.Consts;
import br.trcs.rc.utils.SecurityUtils;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
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
	private static final String profile = "user";
	
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
		return profile;
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
		if (user.getBirthDate().isAfter(LocalDate.now())) {
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inválida", "A data de nascimento não pode ser maior que hoje"));
		    return "adduser";
		}

        try {
        	// Remove formatação do CPF antes de salvar.
        	String cpfClean = user.getCpf().replace(".", "").replace("-", "");
        	user.setCpf(cpfClean);
        	user.setProfile(profile);
        	
        	user.setConfirmedEmail(false); 
        	user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        	
        	System.out.println(user.getProfile());
        	
            DAO<User> dao = new DAO<User>(User.class);
            dao.insert(user);
            
            // Envia e-mail de confirmação de cadastro com link de ativação.
            boolean emailSent = EmailService.sendActivationEmail(user.getEmail(), user.getName(), user.getCpf());
            
            if (emailSent) 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado", "Verifique seu e-mail para ativar a conta."));
            else 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cadastro realizado", "Sua conta não está ativa"));
            
            user = new User(); // Limpa o formulário.
            
            return "adduser";
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao registrar usuário", e.getMessage()));
        }
        
        return "adduser";
    }
}
