package pl.ftt.security.token;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * User: rafal.glowacz
 * Date: 10/14/13
 */
public class WicketUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private Roles roles = new Roles();

    public WicketUsernamePasswordAuthenticationToken(Object principal, Object credentials,
                                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        mapRoles(authorities);
    }

    private void mapRoles(Collection<? extends GrantedAuthority> authorities) {
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                roles.add(authority.getAuthority());
            }
        }
    }

    public Roles getRoles() {
        return this.roles;
    }
}
