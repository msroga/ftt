package pl.myo.gui.user.components;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.myo.core.component.portlet.AbstractReadOnlyPanel;
import pl.myo.core.component.portlet.BootstrapViewLabel;
import pl.myo.core.component.portlet.GenderValuesFormatter;
import pl.myo.domain.User;

/**
 * Created by Marek on 2015-05-27.
 */
public class UserReadOnlyPanel extends AbstractReadOnlyPanel<User>
{
   public UserReadOnlyPanel(String id, IModel<User> model)
   {
      super(id, model);

      add(newViewLabel("loginField", new ResourceModel("user.login"), new PropertyModel<>(model, User.FIELD_LOGIN)));
      add(newViewLabel("nameField", new ResourceModel("user.name"), new PropertyModel<>(model, User.FIELD_NAME)));
      add(newViewLabel("surnameField", new ResourceModel("user.surname"), new PropertyModel<>(model, User.FIELD_SURNNAME)));
      add(newViewLabel("emailField", new ResourceModel("user.email"), new PropertyModel<>(model, User.FIELD_EMAIL)));
      add(newViewLabel("typeField", new ResourceModel("user.type"), new PropertyModel<>(model, User.FIELD_TYPE)));
      BootstrapViewLabel genderField = newViewLabel("genderField", new ResourceModel("user.gender"),
              new PropertyModel<>(model, User.FIELD_GENDER));
      genderField.getField().setValuesFormatter(new GenderValuesFormatter());
      add(genderField);
      add(newViewLabel("socialNumberField", new ResourceModel("user.social.number"), new PropertyModel<>(model, User.FIELD_SOCIAL_NUMBER)));
   }
}
