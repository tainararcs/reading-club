package br.trcs.rc.mb;

import java.io.Serializable;
import java.util.List;

import br.trcs.rc.dao.ComicDAO;
import br.trcs.rc.dao.DAO;
import br.trcs.rc.model.Comic;
import br.trcs.rc.utils.Consts;
import br.trcs.rc.utils.FacesMessages;
import br.trcs.rc.utils.MessagesConsts;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Managed Bean responsável pelo gerenciamento das revistas (comics).
 * O escopo {@link ViewScoped} garante que os dados permaneçam durante o ciclo de vida da view atual.
 * </p>
 */
@Named(Consts.COMIC_MB)
@ViewScoped
public class ComicMB implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * Entidade Comic utilizada no formulário de cadastro.
     */
	private Comic comic = new Comic();

	/**
     * Lista de todas as revistas cadastradas.
     */
	private List<Comic> comicsList;

	/**
     * Lista de revistas disponíveis para empréstimo.
     */
	private List<Comic> availablesList;
	
	/**
     * Bean de login utilizado para controle de acesso.
     */
	@Inject
    private LoginMB loginMB;   
	
	/**
     * Retorna a revista em edição no formulário.
     *
     * @return comic.
     */
	public Comic getComic() {
		return comic;
	}
	
	/**
     * Retorna a lista de todas as revistas cadastradas.
     *
     * @return lista de revistas.
     */
	public List<Comic> getComicsList() {
		if (comicsList == null) {
            DAO<Comic> dao = new DAO<>(Comic.class);
            comicsList = dao.list(); 
        }
		return comicsList;
	}
	
	/**
     * Retorna a lista de revistas disponíveis para empréstimo.
     *
     * @return lista de revistas disponíveis.
     */
	public List<Comic> getAvailablesList() {
		if (availablesList == null) {
            ComicDAO dao = new ComicDAO(Comic.class);
            availablesList = dao.listAvailableComics(); 
        }
		return availablesList;
	}

	/**
     * Realiza o cadastro de uma nova revista.
     * Apenas usuários com perfil ADMIN podem realizar esta operação.
     *
     * @return página de cadastro de revista.
     */
	public String insert() {
		// Interrompe a execução do método.
		if (!checkUserValues()) return null;
				
        try {
        	comic.setAvailability(true);
        	
            DAO<Comic> dao = new DAO<Comic>(Comic.class);
            dao.insert(comic);

            FacesMessages.addInfoMessage(MessagesConsts.ADD_COMIC_SUCCESS);
            comic = new Comic(); // Limpa o formulário.
        } catch (Exception e) {
        	if (e.getMessage().contains("duplicate key"))
        		FacesMessages.addErrorMessage(MessagesConsts.ALREADY_COMIC_ERROR);
	       	else 
	       		FacesMessages.addErrorMessage(MessagesConsts.ADD_COMIC_ERROR);
            return null;
        }
        
        return Consts.ADD_COMIC_PAGE;
	}
	
	private boolean checkUserValues() {
		if (!loginMB.isAdmin()) { 
	        try {
	            FacesContext.getCurrentInstance().getExternalContext().redirect(Consts.ACCESS_DENIED_HTML);
	        } catch (Exception ignored) {}
	        return false; 
		}
		
		if (comic.getCollection() == null || comic.getCollection().isBlank()) {
			FacesMessages.addErrorMessage(MessagesConsts.EMPTY_FIELDS_ERROR);
    	    return false;
    	}
		
		if (comic.getEditionNumber() == null) {
			FacesMessages.addErrorMessage(MessagesConsts.EMPTY_FIELDS_ERROR);
    	    return false;
    	}
		
		if (comic.getBoxId() == null) {
			FacesMessages.addErrorMessage(MessagesConsts.SELECT_COMIC_ERROR);
    	    return false;
    	}
		return true;
	}
	
}
