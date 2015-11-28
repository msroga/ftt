package pl.ftt.gui.user.cmp;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.core.component.portlet.AbstractReadOnlyPanel;
import pl.ftt.domain.User;

/**
 * Created by Marek on 2015-11-28.
 */
public class UserReadOnlyPanel extends AbstractReadOnlyPanel<User>
{
   public UserReadOnlyPanel(String id, IModel<User> model)
   {
      super(id, model);
      add(newViewLabel("login", new ResourceModel("login.field"), User.FIELD_LOGIN).setLabelSize(3).setSize(9));
      add(newViewLabel("email", new ResourceModel("email.field"), User.FIELD_EMAIL).setLabelSize(3).setSize(9));
      add(newViewLabel("firstName", new ResourceModel("first.name.field"), User.FIELD_FIRST_NAME).setLabelSize(3).setSize(9));
      add(newViewLabel("lastName", new ResourceModel("last.name.field"), User.FIELD_LAST_NAME).setLabelSize(3).setSize(9));
      add(newViewLabel("type", new ResourceModel("type.field"), User.FIELD_TYPE).setLabelSize(3).setSize(9));
   }
}
