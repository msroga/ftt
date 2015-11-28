package pl.ftt.gui.user.cmp;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import pl.ftt.core.component.form.EnumDropDownWithLabel;
import pl.ftt.core.component.form.PasswordFieldWithLabel;
import pl.ftt.core.component.form.TextFieldWithLabel;
import pl.ftt.core.component.portlet.AbstractEditablePanel;
import pl.ftt.domain.Password;
import pl.ftt.domain.User;
import pl.ftt.domain.utils.UserTypeEnum;
import pl.ftt.gui.validators.UniqueEmailValidator;
import pl.ftt.gui.validators.UniqueLoginValidator;
import pl.ftt.gui.validators.UserPasswordValidator;

/**
 * Created by Marek on 2015-11-28.
 */
public class UserEditablePanel extends AbstractEditablePanel<User>
{
   private final IModel<String> confirmPasswordModel = new Model<>();

   public UserEditablePanel(String id, IModel<User> model, final IModel<String> passwordModel)
   {
      super(id, model);

      TextFieldWithLabel<String> loginField = new TextFieldWithLabel<String>(
              "login",
              new PropertyModel<String>(model, User.FIELD_LOGIN),
              new ResourceModel("login.field"),
              true,
              new UniqueLoginValidator(model));
      add(loginField);

      TextFieldWithLabel<String> emailField = new TextFieldWithLabel<String>(
              "email",
              new PropertyModel<String>(model, User.FIELD_EMAIL),
              new ResourceModel("email.field"),
              true,
              EmailAddressValidator.getInstance(),
              new UniqueEmailValidator(model));
      add(emailField);

      TextFieldWithLabel<String> firstNameField = new TextFieldWithLabel<String>(
              "firstName",
              new PropertyModel<String>(model, User.FIELD_FIRST_NAME),
              new ResourceModel("first.name.field"),
              true);
      add(firstNameField);

      TextFieldWithLabel<String> lastNameField = new TextFieldWithLabel<String>(
              "lastName",
              new PropertyModel<String>(model, User.FIELD_LAST_NAME),
              new ResourceModel("last.name.field"),
              true);
      add(lastNameField);

      EnumDropDownWithLabel<UserTypeEnum> typeField = new EnumDropDownWithLabel<UserTypeEnum>(
              "type",
              new PropertyModel<UserTypeEnum>(model, User.FIELD_TYPE),
              new ResourceModel("type.field"),
              true,
              UserTypeEnum.class);
      add(typeField);

      Form passwordForm = new Form("passwordForm");
      PasswordFieldWithLabel passwordField = new PasswordFieldWithLabel(
              "password.pass",
              passwordModel,
              new ResourceModel("password.pass"),
              true,
              true,
              StringValidator.maximumLength(Password.FIELD_PASSWORD_LENGTH))
      {
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            setRequired(getModelObject().getId() == null);
         }
      };
      passwordForm.add(passwordField);

      PasswordFieldWithLabel confirmPasswordField = new PasswordFieldWithLabel(
              "password.confirmPass",
              confirmPasswordModel,
              new ResourceModel("password.confirmPass"),
              true,
              true,
              StringValidator.maximumLength(Password.FIELD_PASSWORD_LENGTH))
      {
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            setRequired(getModelObject().getId() == null);
         }
      };
      passwordForm.add(confirmPasswordField);
      passwordForm.add(new UserPasswordValidator(passwordField.getField(), confirmPasswordField.getField()));

      Label passwordInformation = new Label("passwordInformation", new ResourceModel("do.not.fill.password"))
      {
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            setVisible(getModelObject().getId() != null);
         }
      };
      add(passwordInformation);
      add(passwordForm);
   }
}
