package pl.myo.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import pl.myo.security.crypto.exception.TokenParseException;
import pl.myo.security.token.UserToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: rafal.glowacz
 * Date: 10/10/13
 */

public final class AuthenticationRememberMeProcessingFilter extends AbstractAuthenticationProcessingFilter implements RememberMeServices {

    public static final int TWO_WEEKS_S = 1209600;

    private boolean rememberMeEnabled = true;

    private int tokenValiditySeconds = TWO_WEEKS_S;

    private String rememberMeParameter = AbstractRememberMeServices.DEFAULT_PARAMETER;

    protected String getToken(HttpServletRequest request)
    {
        if (rememberMeEnabled)
        {
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
            {
                for (Cookie cookie : cookies)
                {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    String header = name + " token=\"" + value + "\"";
                    if (isSchemeSupported(header))
                    {
                        logAuthorizationToken(header);
                        return value;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response)
    {
        return attemptAuthentication(request);
    }

    public final void loginFail(HttpServletRequest request, HttpServletResponse response)
    {
        logger.debug("Interactive login attempt was unsuccessful.");
        cancelCookie(request, response);
        onLoginFail(request, response);
    }

    private void cancelCookie(HttpServletRequest request, HttpServletResponse response)
    {
        logger.debug("Cancelling cookie");
        Cookie cookie = new Cookie(tokenName, null);
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());

        response.addCookie(cookie);
    }

    protected void onLoginFail(HttpServletRequest request, HttpServletResponse response)
    {
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {

        if (!rememberMeEnabled)
        {
            return;
        }

        if (!rememberMeRequested(request, rememberMeParameter))
        {
            logger.debug("Remember-me login not requested.");
            return;
        }

        onLoginSuccess(request, response, authentication);
    }

    protected boolean rememberMeRequested(HttpServletRequest request, String parameter)
    {
        String paramValue = request.getParameter(parameter);

        if (paramValue != null)
        {
            if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
                    || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1"))
            {
                return true;
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Did not send remember-me cookie (principal did not set parameter '" + parameter + "')");
        }

        return false;
    }

    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        String token = makeToken(authentication);
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(tokenValiditySeconds);
        response.addCookie(cookie);
    }

    private String makeToken(Authentication authentication)
    {
        try
        {
            return marshaller.marshall(new UserToken(
                    ((User) authentication.getPrincipal()).getUsername(),
                    tokenValiditySeconds));
        }
        catch (TokenParseException e)
        {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public void setRememberMeParameter(String rememberMeParameter)
    {
        this.rememberMeParameter = rememberMeParameter;
    }

    public void setTokenValiditySeconds(int tokenValiditySeconds)
    {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    public void setRememberMeEnabled(boolean rememberMeEnabled)
    {
        this.rememberMeEnabled = rememberMeEnabled;
    }
}

