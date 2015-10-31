package pl.ftt.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.Assert;
import pl.ftt.core.menu.MenuHolder;
import pl.ftt.core.menu.MenuItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SuccessfulLoginHandler extends SimpleUrlAuthenticationSuccessHandler implements InitializingBean
{
   private String accessDeniedPageUrl;

   @Override
   public void afterPropertiesSet() throws Exception
   {
      Assert.notNull(accessDeniedPageUrl);
   }

   @Override
   protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response)
   {
      return getMenuTarget();
   }

   private String getMenuTarget()
   {
      MenuItem menuItem = MenuHolder.getInstance().getMenuTarget();
      if (menuItem != null)
      {
            return menuItem.getTargetUrl();
      }
      else
      {
         return accessDeniedPageUrl;
      }
   }

   public String getAccessDeniedPageUrl()
   {
      return accessDeniedPageUrl;
   }

   public void setAccessDeniedPageUrl(String accessDeniedPageUrl)
   {
      this.accessDeniedPageUrl = accessDeniedPageUrl;
   }
}
