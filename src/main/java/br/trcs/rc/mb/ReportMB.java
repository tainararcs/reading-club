package br.trcs.rc.mb;

import java.io.Serializable;
import java.util.List;

import br.trcs.rc.dao.BorrowingDAO;
import br.trcs.rc.model.Borrowing;
import br.trcs.rc.utils.Consts;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Managed Bean responsável pela geração de relatórios de empréstimos.
 * <br>
 * Fornece dados para visualização de:
 * </p>
 * <ul>
 *   <li>Empréstimos em andamento</li>
 *   <li>Empréstimos em atraso</li>
 *   <li>Empréstimos de um usuário específico</li>
 * </ul>
 * <p>
 */
@Named(Consts.REPORT_MB)
@ViewScoped
public class ReportMB implements Serializable {

	private static final long serialVersionUID = -6954681754915472551L;
	
    /**
     * Lista de empréstimos atualmente em andamento.
     */
    private List<Borrowing> allBorrowings;

    /**
     * Lista de empréstimos em atraso.
     */
    private List<Borrowing> overdueBorrowings;

    /**
     * Lista de empréstimos filtrados por usuário.
     */
    private List<Borrowing> userBorrowings;

    /**
     * CPF do usuário utilizado como filtro nos relatórios.
     */
    private String userCpf;

    /**
     * Retorna a lista de todos os empréstimos em andamento.
     *
     * @return lista de empréstimos não devolvidos.
     */
    public List<Borrowing> getAllBorrowings() {
        if (allBorrowings == null) {
            BorrowingDAO dao = new BorrowingDAO(Borrowing.class);
            allBorrowings = dao.listBorrowed();
        }
        return allBorrowings;
    }

    /**
     * Retorna a lista de empréstimos em atraso.
     *
     * @return lista de empréstimos atrasados.
     */
    public List<Borrowing> getOverdueBorrowings() {
        if (overdueBorrowings == null) {
            BorrowingDAO dao = new BorrowingDAO(Borrowing.class);
            overdueBorrowings = dao.listOverdue();
        }
        return overdueBorrowings;
    }

    /**
     * Retorna a lista de empréstimos de um usuário específico.
     * O filtro é aplicado com base no CPF informado.
     *
     * @return lista de empréstimos do usuário.
     */
    public List<Borrowing> getUserBorrowings() {
        if (userBorrowings == null && userCpf != null && !userCpf.isEmpty()) {
            BorrowingDAO dao = new BorrowingDAO(Borrowing.class);
            userBorrowings = dao.listAllInfoByUser(userCpf);
        }
        return userBorrowings;
    }

    /**
     * Aplica o filtro por usuário.
     * Força o recarregamento da lista de empréstimos com base no CPF informado.
     */
    public void filterByUser() {
        userBorrowings = null;
        getUserBorrowings();
    }

    /**
     * Retorna o CPF utilizado como filtro.
     *
     * @return CPF do usuário.
     */
    public String getUserCpf() {
        return userCpf;
    }

    /**
     * Define o CPF utilizado como filtro.
     *
     * @param userCpf CPF do usuário.
     */
    public void setUserCpf(String userCpf) {
        this.userCpf = userCpf;
    }
}
