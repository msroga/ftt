package pl.myo.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import pl.myo.security.token.WicketUsernamePasswordAuthenticationToken;

/**
 * User: rafal.glowacz
 * Date: 10/14/13
 */
public class WicketServiceAuthenticationProvider extends ServiceAuthenticationProvider
{

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        WicketUsernamePasswordAuthenticationToken result = new WicketUsernamePasswordAuthenticationToken(
                principal,
                authentication.getCredentials(),
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());

        return result;
    }

    @Override
    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        super.setAuthoritiesMapper(authoritiesMapper);
        this.authoritiesMapper = authoritiesMapper;
    }
}
