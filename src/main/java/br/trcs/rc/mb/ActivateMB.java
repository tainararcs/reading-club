package br.trcs.rc.mb;

import br.trcs.rc.dao.DAO;
import br.trcs.rc.model.User;
import br.trcs.rc.utils.Consts;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Managed Bean responsável pela ativação de contas de usuários.
 * <br>
 * Este bean é utilizado no fluxo de confirmação de e-mail,
 * normalmente acessado por meio de um link enviado ao usuário após o cadastro.
 * <br>
 * Ao receber o CPF do usuário, o bean localiza o registro correspondente e marca o e-mail como confirmado.
 */
@Named(Consts.ACTIVE_MB)
@ViewScoped
public class ActivateMB implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	/**
     * CPF do usuário a ser ativado.
     */
	private String cpf;

	/**
     * Retorna o CPF do usuário.
     *
     * @return CPF do usuário.
     */
	public String getCpf() {
		return cpf;
	}

	/**
     * Define o CPF do usuário.
     *
     * @param cpf CPF do usuário.
     */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	/**
     * Ativa a conta do usuário.
     */
    public void activate() {
    	if (cpf == null) return;

    	DAO<User> dao = new DAO<>(User.class);
        User user = dao.findByCpf(cpf);

        if (user != null) {
            user.setConfirmedEmail(true);
            dao.update(user);
        }
    }
}
