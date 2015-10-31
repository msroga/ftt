package pl.ftt.security.access;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * User: rafal.glowacz
 * Date: 10/11/13
 */
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {
//    private static Logger LOGGER = Logger.getLogger(Http401UnauthorizedEntryPoint.class);

    private static final String JSON = "application/json";

    private static final String XML = "application/xml";

    private static final String ACCEPT = "Accept";

    private String loginPage = "/login";

    /**
     * Always returns a 401 error code to the client.
     */
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException, ServletException {
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug(ex.getMessage());
//        }

        if (hasClientAcceptHeader(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.sendRedirect(request.getContextPath() + loginPage);
        }
    }

    private boolean hasClientAcceptHeader(HttpServletRequest request) {
        Enumeration headers = request.getHeaders(ACCEPT);
        if (headers == null) {
            return false;
        }
        for (; headers.hasMoreElements(); ) {
            String header = (String) headers.nextElement();
            if (StringUtils.startsWith(header, JSON) ||
                    StringUtils.startsWith(header, XML)) {
                return true;
            }
        }
        return false;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}

