package pl.ftt.domain;

import org.springframework.security.core.GrantedAuthority;
import pl.ftt.domain.utils.UserTypeEnum;

import java.util.Collection;

/**
 * Created by Marek on 2015-05-22.
 */
public class UserDetails extends org.springframework.security.core.userdetails.User
{
   private final User user;

   public UserDetails(String username, String password,
                      Collection<? extends GrantedAuthority> authorities, User user)
   {
      super(username, password, authorities);
      this.user = user;
   }

   public boolean isAdministrator()
   {
      return user.getType() == UserTypeEnum.ADMIN;
   }

   public boolean isUser()
   {
      return user.getType() == UserTypeEnum.USER;
   }
}
