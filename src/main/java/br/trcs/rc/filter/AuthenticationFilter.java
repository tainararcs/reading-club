package br.trcs.rc.filter;

import java.io.IOException;

import br.trcs.rc.mb.LoginMB;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticação e autorização. Intercepta todas as requisições para páginas {@code .xhtml}.
 * Protege páginas que requerem login e controla acesso baseado em perfil (ADMIN/USER).
 */
@WebFilter("*.xhtml")
public class AuthenticationFilter implements Filter {

	/**
     * Managed Bean responsável pelo controle de autenticação e informações do usuário logado.
     */
    @Inject
    private LoginMB loginMB;

    /**
     * Intercepta as requisições HTTP para páginas {@code .xhtml} e aplica as regras de autenticação e autorização.
     *
     * @param request  requisição do cliente.
     * @param response resposta HTTP.
     * @param chain    cadeia de filtros.
     * @throws IOException      em caso de erro de redirecionamento.
     * @throws ServletException em caso de falha no processamento do filtro.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        boolean isPublicPage = requestURI.endsWith("login.xhtml")
        					|| requestURI.contains("activate.xhtml")
                            || requestURI.endsWith("accessdenied.xhtml")
                            || requestURI.contains("/resources/")
                            || requestURI.contains("/jakarta.faces.resource/")
                            || requestURI.contains("/javax.faces.resource/");

        // Se for página pública, permite acesso.
        if (isPublicPage) {
            chain.doFilter(request, response);
            return;
        }

        // Verifica se o usuário está logado.
        boolean isLoggedIn = (loginMB != null && loginMB.getLoggedUser() != null);

        // Se não está logado, redireciona para login.
        if (!isLoggedIn) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }

        // Controle de acesso por perfil.
        boolean isAdmin = loginMB.isAdmin();
        boolean isAdminOnlyPage = requestURI.endsWith("adduser.xhtml")
                               || requestURI.endsWith("addcomic.xhtml")
                               || requestURI.endsWith("addbox.xhtml")
                               || requestURI.endsWith("showreport.xhtml");

        if (isAdminOnlyPage && !isAdmin) {
            res.sendRedirect(req.getContextPath() + "/accessdenied.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }
}