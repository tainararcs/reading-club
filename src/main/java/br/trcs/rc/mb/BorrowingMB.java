package br.trcs.rc.mb;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import br.trcs.rc.dao.BorrowingDAO;
import br.trcs.rc.dao.DAO;
import br.trcs.rc.dao.UserDAO;
import br.trcs.rc.model.Borrowing;
import br.trcs.rc.model.Comic;
import br.trcs.rc.model.User;
import br.trcs.rc.utils.Consts;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Managed Bean responsável pelo gerenciamento de empréstimos.
 * O escopo {@link ViewScoped} mantém os dados durante o ciclo de vida da view atual.
 */
@Named(Consts.BORROWING_MB)
@ViewScoped
public class BorrowingMB implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * Identificador da revista selecionada no formulário.
     */
	private Integer comicId;

	/**
     * CPF do usuário selecionado (utilizado apenas por administradores).
     */
	private String userCpf;

	/**
     * Entidade Borrowing utilizada no formulário de cadastro.
     */
	private Borrowing borrowing = new Borrowing();

	/**
     * Lista de empréstimos exibida na tela.
     */
	private List<Borrowing> borrowingsList;

    /**
     * Bean de login utilizado para controle de acesso e perfil.
     */
    @Inject
    private LoginMB loginMB;   
    
    /**
     * Retorna o ID da revista selecionada.
     *
     * @return comicId.
     */
	public Integer getComicId() {
		return comicId;
	}
	
	/**
     * Define o ID da revista selecionada.
     *
     * @param comicId identificador da revista.
     */
	public void setComicId(Integer comicId) {
	    this.comicId = comicId;
	}
	
	/**
     * Retorna o CPF do usuário selecionado.
     *
     * @return CPF do usuário.
     */
	public String getUserCpf() {
		return userCpf;
	}

	/**
     * Define o CPF do usuário selecionado.
     *
     * @param userCpf CPF do usuário.
     */
	public void setUserCpf(String userCpf) {
		this.userCpf = userCpf;
	}

	/**
     * Retorna o empréstimo em edição.
     *
     * @return borrowing.
     */
	public Borrowing getBorrowing() {
		return borrowing;
	}

	/**
     * Retorna a lista de empréstimos conforme o perfil do usuário.
     * <ul>
     *   <li>ADMIN: lista todos os empréstimos ou filtra por CPF</li>
     *   <li>USER: lista apenas os próprios empréstimos</li>
     * </ul>
     *
     * @return lista de empréstimos.
     */

	public List<Borrowing> getBorrowingsList() {
        if (borrowingsList == null) {
            BorrowingDAO dao = new BorrowingDAO(Borrowing.class);

            if (loginMB.isAdmin()) {
            	if (userCpf != null && !userCpf.trim().isEmpty()) 
                    borrowingsList = dao.listAllInfoByUser(userCpf);
                else 
                    borrowingsList = dao.listAllInfo();
            }
            else 
                borrowingsList = dao.listAllInfoByUser(loginMB.getLoggedUser().getCpf());
        }
        return borrowingsList;
    }
	
	/**
     * Inicializa o formulário de empréstimo.
     * Define a data de checkout como hoje  e 
     * a data prevista de devolução para 7 dias após.
     */
	@PostConstruct
	public void init() {
	    borrowing = new Borrowing();
	    borrowing.setCheckoutDate(LocalDate.now());
	    borrowing.setExpectedReturnDate(LocalDate.now().plusDays(7));
	}

	/**
     * Registra um novo empréstimo.
     *
     * @return página de cadastro de empréstimo.
     */
	public String insert() {
		System.out.println("CPF do usuário: " + borrowing.getUser() + " Id da revista: " + comicId);
		System.out.println("Data de checkout: " + borrowing.getCheckoutDate()  + " Data para return: " + borrowing.getExpectedReturnDate());

	    try {
	    	if (!checkUser()) return "addborrowing";
	        
	        // Verifica revista.
	    	DAO<Comic> daoComic = new DAO<>(Comic.class);

	    	if (comicId == null) {
	    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione uma revista"));
	    	    return "addborrowing";
	    	}
	    	
	    	Comic comic = daoComic.findById(comicId);
	    	if (comic == null)
	    	    throw new RuntimeException("Revista não encontrada");

	        // Marca como indisponível.
	        comic.setAvailability(false);
	        daoComic.update(comic);
	        
	        // Vincula revista ao empréstimo.
	        borrowing.setComic(comic);
	        
	        User user;
	        if (loginMB.isAdmin()) 
	            user = new UserDAO(User.class).findByCpf(userCpf);
	        else 
	            user = loginMB.getLoggedUser();

	        borrowing.setUser(user);

	        // Salva empréstimo.
	        DAO<Borrowing> dao = new DAO<>(Borrowing.class);
	        dao.insert(borrowing);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empréstimo registrado com sucesso"));

            borrowing = new Borrowing(); // Limpa o formulário.
            return "addborrowing";
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao registrar empréstimo", e.getMessage()));
        }
	    
	    return "addborrowing";
    }
	
	/**
     * Registra a devolução imediata de um empréstimo.
     *
     * @param borrowing empréstimo a ser devolvido.
     */
    public void returnToday(Borrowing borrowing) {
        try {
        	borrowing.setReturnDate(LocalDate.now());

            DAO<Borrowing> dao = new DAO<>(Borrowing.class);
            dao.update(borrowing);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Devolução  de registrada", "Empréstimo " + borrowing.getId() + " devolvido hoje"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao registrar devolução", e.getMessage()));
        }
    }
    
    /**
     * Verifica se o usuário pode realizar um novo empréstimo.
     * Um usuário não pode ter mais de um empréstimo em aberto.
     *
     * @return true se permitido, false caso contrário.
     */
    private boolean checkUser() {
    	// CPF do usuário do empréstimo.
        String cpf;

        if (loginMB.isAdmin()) 
            cpf = userCpf; 
        else 
            cpf = loginMB.getLoggedUser().getCpf(); 

        // Verifica se usuário já possui empréstimo aberto.
        BorrowingDAO borrowingDAO = new BorrowingDAO(Borrowing.class);
        if (borrowingDAO.hasOpenBorrowing(cpf)) {
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não permitido", "Você já possui um empréstimo em aberto"));
            return false;
        }
        return true;
    }
    
    /**
     * Aplica filtro de empréstimos por usuário.
     * Força o recarregamento da lista com o CPF informado.
     */
    public void filterByUser() {
    	borrowingsList = null; // força recarregar com o novo CPF.
        getBorrowingsList();
    }
}
