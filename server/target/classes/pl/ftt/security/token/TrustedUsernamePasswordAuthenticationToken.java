package pl.ftt.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * User: rafal.glowacz
 * Date: 10/10/13
 */
public class TrustedUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public TrustedUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
