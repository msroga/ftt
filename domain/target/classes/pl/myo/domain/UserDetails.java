package pl.myo.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Marek on 2015-05-22.
 */
public class UserDetails extends org.springframework.security.core.userdetails.User
{
   private final UserTypeEnum userType;

   public UserDetails(String username, String password,
                      Collection<? extends GrantedAuthority> authorities, User user)
   {
      super(username, password, authorities);
      this.userType = user.getType();
   }

   public boolean isAdministrator()
   {
      return userType == UserTypeEnum.ADMIN;
   }

   public boolean isDoctor()
   {
      return userType == UserTypeEnum.DOCTOR;
   }

   public boolean isUser()
   {
      return userType == UserTypeEnum.USER;
   }
}
