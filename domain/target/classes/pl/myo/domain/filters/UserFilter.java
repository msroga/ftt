package pl.myo.domain.filters;

import pl.myo.domain.User;
import pl.myo.domain.UserTypeEnum;

/**
 * Created by Marek on 2015-05-26.
 */
public class UserFilter implements IFilter
{
   public static final String FIELD_LOGIN = "login";

   public static final String FIELD_NAME = "name";

   public static final String FIELD_EMAIL = "email";

   public static final String FIELD_TYPE = "type";

   public static final String FIELD_ASSIGNED  = "assigned";

   public static final String FIELD_LOGGED = "logged";

   private String login;

   private String name;

   private String email;

   private UserTypeEnum type;

   private Boolean assigned;

   private User logged;

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public UserTypeEnum getType()
   {
      return type;
   }

   public void setType(UserTypeEnum type)
   {
      this.type = type;
   }

   public Boolean getAssigned()
   {
      return assigned;
   }

   public void setAssigned(Boolean assigned)
   {
      this.assigned = assigned;
   }

   public User getLogged()
   {
      return logged;
   }

   public void setLogged(User logged)
   {
      this.logged = logged;
   }
}
