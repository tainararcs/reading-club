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
                    borrowingsList = dao.listAllByUser(userCpf);
                else 
                    borrowingsList = dao.listAll();
            }
            else 
                borrowingsList = dao.listAllByUser(loginMB.getLoggedUser().getCpf());
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
		if (comicId == null) {
    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, Consts.SELECT_COMIC_ERROR));
    	    return Consts.ADD_BORROW_PAGE;
    	}
		
	    try {
	    	User user = checkUser();
	    	if (user == null) return Consts.ADD_BORROW_PAGE;
	    	
	    	Comic comic = checkComic();
	    	if (comic == null) return Consts.ADD_BORROW_PAGE;
	    	
	        // Vincula o usuário e a revista ao empréstimo.
	        borrowing.setUser(user);
	        borrowing.setComic(comic);

	        // Salva empréstimo.
	        DAO<Borrowing> dao = new DAO<>(Borrowing.class);
	        dao.insert(borrowing);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, Consts.ADD_BORROW_SUCCESS));

            borrowing = new Borrowing(); // Limpa o formulário.
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, Consts.ADD_BORROW_ERROR));
        }
	    
	    return Consts.ADD_BORROW_PAGE;
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

            FacesContext.getCurrentInstance().addMessage(
            	null, 
            	new FacesMessage(FacesMessage.SEVERITY_INFO, null, Consts.RETURN_COMIC_SUCCESS + String.format(" (%d)", borrowing.getId()))
            );
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, Consts.RETURN_COMIC_ERROR));
        }
    }
    
    /**
     * Verifica se o usuário pode realizar um novo empréstimo.
     * Um usuário não pode ter mais de um empréstimo em aberto.
     *
     * @return true se permitido, false caso contrário.
     */
    private User checkUser() {
    	User user = null;
    	
    	if (loginMB.isAdmin()) 
            user = new UserDAO(User.class).findByCpf(userCpf);
        else 
            user = loginMB.getLoggedUser();
    	
        // Verifica se usuário já possui empréstimo em aberto.
        BorrowingDAO borrowingDAO = new BorrowingDAO(Borrowing.class);
        if (user != null && borrowingDAO.hasOpenBorrowing(user.getCpf())) {
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, Consts.ALREADY_BORROW_ERROR));
        	return null;
        }
        return user;
    }
    
    /**
     * Verifica se a revista existente está disponível para empréstimo.
     * Em caso positivo, atualiza o status da mesma para 'indisponível'.
     * @return
     */
    private Comic checkComic() {
    	DAO<Comic> daoComic = new DAO<>(Comic.class);
    	
    	Comic comic = daoComic.findById(comicId);
    	if (comic == null || comic.getAvailability() == false)
    	    throw new RuntimeException(Consts.UNAVAIABLE_COMIC_ERROR);

        // Marca como indisponível.
        comic.setAvailability(false);
        daoComic.update(comic);
        
        return comic;
    }
    
    /**
     * Aplica filtro de empréstimos por usuário.
     * Força o recarregamento da lista com o CPF informado.
     */
    public void filterByUser() {
    	borrowingsList = null; // Força recarregar com o novo CPF.
        getBorrowingsList();
    }
}
