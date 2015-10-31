package pl.ftt.security.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by Marek on 2015-05-22.
 */
public class FttServiceAuthenticationProvider extends WicketServiceAuthenticationProvider
{
//   private ILoginAttemptsService loginAttemptsService;
//
//   private IUserLittleService userLittleService;
//
//   private IAdminHistoryCreator adminHistoryCreator;
//
//   private IHistoryService historyService;

   @Override
   protected void doAfterPropertiesSet() throws Exception
   {
      super.doAfterPropertiesSet();
//      Assert.notNull(loginAttemptsService, "A LoginAttempts Service must be set");
//      Assert.notNull(userLittleService, "A UserLittleService Service must be set");
//      Assert.notNull(adminHistoryCreator, "A AdminHistoryCreator Creator must be set");
//      Assert.notNull(historyService, "A HistoryService Service must be set");
   }

   public Authentication authenticate(Authentication authentication) throws AuthenticationException
   {
      try
      {
         Authentication result = super.authenticate(authentication);
//         loginAttemptsService.resetCounter(authentication.getName());
//         UserLittle userLittle = userLittleService.getByLogin(authentication.getName());
//         userLittle.setLastLoginDate(DateTime.now());
//         userLittleService.update(userLittle);
//         History history = adminHistoryCreator.getUserLoginSuccessEntry();
//         history.setUser(userLittle);
//         historyService.save(userLittle, history);
         return result;
      }
      catch (BadCredentialsException e)
      {
//         loginAttemptsService.incrementCounter(authentication.getName());
//         History history = adminHistoryCreator.getUserLoginFailureEntry();
//         UserLittle userLittle = userLittleService.getByLogin(authentication.getName());
//         history.setUser(userLittle);
//         historyService.save(userLittle, history);
         throw e;
      }
   }

//   public ILoginAttemptsService getLoginAttemptsService()
//   {
//      return loginAttemptsService;
//   }
//
//   public void setLoginAttemptsService(ILoginAttemptsService loginAttemptsService)
//   {
//      this.loginAttemptsService = loginAttemptsService;
//   }
//
//   public IUserLittleService getUserLittleService()
//   {
//      return userLittleService;
//   }
//
//   public void setUserLittleService(IUserLittleService userLittleService)
//   {
//      this.userLittleService = userLittleService;
//   }
//
//   public IAdminHistoryCreator getAdminHistoryCreator()
//   {
//      return adminHistoryCreator;
//   }
//
//   public void setAdminHistoryCreator(IAdminHistoryCreator adminHistoryCreator)
//   {
//      this.adminHistoryCreator = adminHistoryCreator;
//   }
//
//   public IHistoryService getHistoryService()
//   {
//      return historyService;
//   }
//
//   public void setHistoryService(IHistoryService historyService)
//   {
//      this.historyService = historyService;
//   }
}
