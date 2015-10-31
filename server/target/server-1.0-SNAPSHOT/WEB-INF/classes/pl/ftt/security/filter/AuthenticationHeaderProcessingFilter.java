package pl.ftt.security.filter;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * User: rafal.glowacz
 * Date: 10/10/13
 */
public class AuthenticationHeaderProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private boolean headerEnabled;

    @SuppressWarnings("unchecked")
    protected String getToken(HttpServletRequest request) {
        if (headerEnabled) {
            Enumeration<String> headers = request.getHeaders(AUTHORIZATION_HEADER);
            if (headers != null) {
                while (headers.hasMoreElements()) {
                    String value = headers.nextElement();
                    if (isSchemeSupported(value)) {
                        logAuthorizationToken(value);
                        return getTokenValue(value);
                    }
                }
            }
        }
        return null;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        this.headerEnabled = headerEnabled;
    }
}
