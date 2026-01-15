package br.trcs.rc.mb;

import java.io.Serializable;
import java.util.List;

import br.trcs.rc.dao.DAO;
import br.trcs.rc.model.Box;
import br.trcs.rc.utils.Consts;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Managed Bean responsável pelo gerenciamento das caixas (Box).
 * O escopo {@link ViewScoped} mantém os dados durante a navegação na mesma view.
 */
@Named(Consts.BOX_MB)
@ViewScoped
public class BoxMB implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * Entidade Box utilizada no formulário de cadastro.
     */
	private Box box = new Box();

	/**
     * Lista de todas as caixas cadastradas.
     */
	private List<Box> boxesList;
	
	/**
     * Bean de login utilizado para controle de acesso.
     */
	@Inject
    private LoginMB loginMB;   
	
	/**
     * Retorna a caixa em edição no formulário.
     *
     * @return box.
     */
	public Box getBox() {
		return box;
	}
	
    /**
     * Retorna a lista de todas as caixas cadastradas.
     *
     * @return lista de caixas
     */
    public List<Box> getBoxesList() {
        if (boxesList == null) {
            DAO<Box> dao = new DAO<>(Box.class);
            boxesList = dao.list(); 
        }
        return boxesList;
    }

	/**
     * Realiza o cadastro de uma nova caixa.
     * Apenas usuários com perfil ADMIN podem realizar esta operação.
     *
     * @return página de cadastro de caixa.
     */
	public String insert() {	
		if (!loginMB.isAdmin()) {
	        try {
	            FacesContext.getCurrentInstance().getExternalContext().redirect("accessdenied.xhtml");
	        } catch (Exception ignored) {}
	        return "addbox";
	    }

        try {
            DAO<Box> dao = new DAO<Box>(Box.class);
            dao.insert(box);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Caixa registrada com sucesso"));

            box = new Box(); // Limpa o formulário.
            return "addbox";
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao registrar caixa", e.getMessage()));
        }
        
        return "addbox";
	}
}
