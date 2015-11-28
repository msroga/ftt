package pl.ftt.gui.user;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.ftt.core.component.portlet.AbstractEditablePanel;
import pl.ftt.core.component.portlet.AbstractReadOnlyPanel;
import pl.ftt.core.component.portlet.PortletPanel;
import pl.ftt.core.pages.AbstractDetailsPage;
import pl.ftt.domain.User;
import pl.ftt.gui.user.cmp.UserEditablePanel;
import pl.ftt.gui.user.cmp.UserReadOnlyPanel;
import pl.ftt.service.IUserService;

/**
 * Created by Marek on 2015-11-28.
 */
public class UserDetailsPage extends AbstractDetailsPage<User>
{
   private final IModel<User> userModel;

   private final IModel<String> passwordModel = new Model<>();

   @SpringBean
   private IUserService userService;

   public UserDetailsPage(IModel<User> model)
   {
      this.userModel = model;
      PortletPanel<User> portletPanel = new PortletPanel<User>("portlet", userModel)
      {

         @Override
         public void onSubmit(AjaxRequestTarget target, User user)
         {
            if (user.getId() == null)
            {
               userService.save(user, passwordModel.getObject());
//               Notification
            }
            else
            {
               userService.update(user, passwordModel.getObject());
            }
         }

         @Override
         public AbstractEditablePanel<User> getEditablePanel(String id, IModel<User> model)
         {
            return new UserEditablePanel(id, model, passwordModel);
         }

         @Override
         public AbstractReadOnlyPanel<User> getReadOnlyPanel(String id, IModel<User> model)
         {
            return new UserReadOnlyPanel(id, model);
         }
      };
      bodyContainer.add(portletPanel);
   }
}
