package pl.ftt.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Marek on 2015-05-22.
 */
public class UserDetails extends org.springframework.security.core.userdetails.User
{
   public UserDetails(String username, String password,
                      Collection<? extends GrantedAuthority> authorities, User user)
   {
      super(username, password, authorities);
   }
}
