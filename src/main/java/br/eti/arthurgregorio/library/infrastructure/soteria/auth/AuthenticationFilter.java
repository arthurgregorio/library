package br.eti.arthurgregorio.library.infrastructure.soteria.auth;

import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 13/11/2018
 */
@WebFilter(urlPatterns = "/secured/*")
public class AuthenticationFilter extends HttpFilter {

    @Inject
    private Logger logger;

    /**
     *
     * @param request
     * @param response
     * @param session
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, HttpSession session, FilterChain chain)
            throws ServletException, IOException {

        final String requestedPath = request.getRequestURI().substring(request.getContextPath().length())
                .replaceAll("[/]+$", "");

        final String user = request.getRemoteUser();

        if (isBlank(user)) {
            this.logger.info(String.format("The user must be authenticated to access this [%s]", requestedPath));
            Servlets.facesRedirect(request, response, "/index.xhtml");
        }
        chain.doFilter(request, response);
    }
}
