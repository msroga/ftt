package pl.myo.security.authentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import pl.myo.security.token.TrustedUsernamePasswordAuthenticationToken;

/**
 * User: rafal.glowacz
 * Date: 10/10/13
 */
public class ServiceAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider implements InitializingBean {

    private PasswordEncoder passwordEncoder;

    private UserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (!(authentication instanceof TrustedUsernamePasswordAuthenticationToken)) {
            String rawPassword = authentication.getCredentials().toString();
            if (StringUtils.isBlank(rawPassword)) {
                throw new BadCredentialsException("Missing password for user " + userDetails.getUsername());
            }
            String password = userDetails.getPassword();
            if (StringUtils.isBlank(password)) {
                throw new BadCredentialsException("Missing password for user " + userDetails.getUsername());
            }
            if (!passwordEncoder.matches(rawPassword, password)) {
                throw new BadCredentialsException("Password for user " + userDetails.getUsername() + " did not match");
            }
        }
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }

        if (loadedUser == null) {
            throw new UsernameNotFoundException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
