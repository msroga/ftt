package pl.myo.gui.user.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.portlet.AbstractEditablePanel;
import pl.myo.core.component.portlet.AbstractReadOnlyPanel;
import pl.myo.core.component.portlet.PortletPanel;
import pl.myo.core.notification.Notification;
import pl.myo.domain.User;
import pl.myo.service.IUserService;

/**
 * Created by Marek on 2015-05-27.
 */
public class UserDetailsPanel extends GenericPanel<User>
{
   private final IModel<String> passwordModel = new Model<String>();

   @SpringBean
   private IUserService userService;

   public UserDetailsPanel(String id, IModel<User> model, boolean editMode)
   {
      super(id, model);

      PortletPanel<User> portlet = new PortletPanel<User>("portlet", model)
      {
         @Override
         public void onSubmit(AjaxRequestTarget target, User object)
         {
            if (object.getId() == null)
            {
               userService.save(object, passwordModel.getObject());
               Notification.success(getString("success.save.user"));
            }
            else
            {
               userService.update(object, passwordModel.getObject());
               Notification.success(getString("success.update.user"));
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
      portlet.setEditMode(editMode);
      add(portlet);
   }
}
