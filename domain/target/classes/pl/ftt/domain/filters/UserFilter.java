package pl.ftt.domain.filters;

/**
 * Created by Marek on 2015-11-28.
 */
public class UserFilter implements IFilter
{
   public static final String FILTER_LOGIN = "login";

   private String login;

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }
}
