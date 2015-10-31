package pl.ftt.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import pl.ftt.domain.UserDetails;
import pl.ftt.service.IConfigurationService;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Marek on 2015-05-22.
 */
public class FttUserDetailsService implements UserDetailsService, InitializingBean
{
//   private IUserService userService;
//
//   private IPasswordService passwordService;

   private IConfigurationService configurationService;

   private PasswordEncoder passwordEncoder;

   private boolean checkAdminIpAddress;

   @Override
   public void afterPropertiesSet() throws Exception
   {
//      Assert.notNull(userService, "User Service is required");
//      Assert.notNull(passwordService, "Password Service is required");
      Assert.notNull(configurationService, "Configuration Service is required");
      Assert.notNull(passwordEncoder, "PasswordEncoder is required");
   }

   @Override
   public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
   {
//      User user = userService.getByLogin(login);
//      if (user == null) // active - not used anymore
//      {
//         throw new UsernameNotFoundException("user '" + login + "' not found!");
//      }

//      checkUserType(user);

//      Password password = passwordService.getByUser(user);
//      if (password == null || StringUtils.isBlank(password.getPassword()))
//      {
//         throw new UsernameNotFoundException("user password '" + login + "' not found!");
//      }

//         List<Authority> authorities = authorityService.find(user, true);
//         List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
//         for (Authority authority : authorities)
//         {
//            roles.add(new SimpleGrantedAuthority(authority.getKey()));
//         }

//      UserDetails result = new UserDetails(login, password.getPassword(), roles, user);
//      return result;
      return null;
   }

//   private void checkUserType(User user) throws UsernameNotFoundException
//   {
//      if (user.isAdministrator() || user.isEmployee())
//      {
//         if (checkAdminIpAddress)
//         {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                    .getRequest();
//            if (!isLocalIpAddress(request.getRemoteAddr()))
//            {
//               throw new UsernameNotFoundException("admin user only allowed to log from local network "
//                       + request.getRemoteAddr());
//            }
//         }
//      }
//      else if (user.isClient() && !user.isSubcontractor())
//      {
//         if (clientService.countActiveClients(user) == 0)
//         {
//            throw new UsernameNotFoundException("client(s) for user " + user + " not active");
//         }
//      }
//   }

   private boolean isLocalIpAddress(String ip)
   {
      try
      {
         InetAddress inetAddress = InetAddress.getByName(ip);
         return inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress();
      }
      catch (UnknownHostException e)
      {
         return false;
      }
   }

//   public IPasswordService getPasswordService()
//   {
//      return passwordService;
//   }
//
//   public void setPasswordService(IPasswordService passwordService)
//   {
//      this.passwordService = passwordService;
//   }

   public boolean isCheckAdminIpAddress()
   {
      return checkAdminIpAddress;
   }

   public void setCheckAdminIpAddress(boolean checkAdminIpAddress)
   {
      this.checkAdminIpAddress = checkAdminIpAddress;
   }

//   public IUserService getUserService()
//   {
//      return userService;
//   }
//
//   public void setUserService(IUserService userService)
//   {
//      this.userService = userService;
//   }

   public IConfigurationService getConfigurationService()
   {
      return configurationService;
   }

   public void setConfigurationService(IConfigurationService configurationService)
   {
      this.configurationService = configurationService;
   }

   public PasswordEncoder getPasswordEncoder()
   {
      return passwordEncoder;
   }

   public void setPasswordEncoder(PasswordEncoder passwordEncoder)
   {
      this.passwordEncoder = passwordEncoder;
   }
}
