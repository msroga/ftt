package pl.ftt.gui.user;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.pages.AbstractPage;
import pl.ftt.domain.User;
import pl.ftt.domain.utils.UserTypeEnum;
import pl.ftt.gui.login.LoginPage;
import pl.ftt.gui.user.cmp.CreatePanel;
import pl.ftt.service.IUserService;

/**
 * Created by Marek on 2015-11-28.
 */
@MountPath(FttApiMappings.REGISTER_PAGE)
public class RegisterPage extends AbstractPage
{
   @SpringBean
   private IUserService userService;

   private IModel<User> newUserModel = new Model();


   public RegisterPage()
   {
     add(new CreatePanel("registerPanel", newUserModel)
     {
        @Override
        protected void onCreate(User user, String pass)
        {
           userService.save(user, pass);
           setResponsePage(LoginPage.class);
        }
     });
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      User user = new User();
      user.setType(UserTypeEnum.USER);
      user.setActive(true);
      newUserModel.setObject(user);
   }
}
