package pl.ftt.gui.user.cmp;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import pl.ftt.core.component.form.PasswordFieldWithLabel;
import pl.ftt.core.component.form.TextFieldWithLabel;
import pl.ftt.domain.Password;
import pl.ftt.domain.User;
import pl.ftt.gui.validators.UniqueEmailValidator;
import pl.ftt.gui.validators.UniqueLoginValidator;
import pl.ftt.gui.validators.UserPasswordValidator;

/**
 * Created by Marek on 2015-11-28.
 */
public abstract class CreatePanel extends GenericPanel<User>
{
   private final IModel<User> newUserModel;

   private final IModel<String> passwordModel = new Model<>();

   private final IModel<String> confirmPasswordModel = new Model<>();

   public CreatePanel(String id, IModel<User> model)
   {
      super(id, model);
      newUserModel = model;

      Form form = new Form("form");
      add(form);

      TextFieldWithLabel<String> loginField = new TextFieldWithLabel<String>(
              "login",
              new PropertyModel<String>(newUserModel, User.FIELD_LOGIN),
              new ResourceModel("login.field"),
              true,
              new UniqueLoginValidator());
      form.add(loginField);

      TextFieldWithLabel<String> emailField = new TextFieldWithLabel<String>(
              "email",
              new PropertyModel<String>(newUserModel, User.FIELD_EMAIL),
              new ResourceModel("email.field"),
              true,
              EmailAddressValidator.getInstance(),
              new UniqueEmailValidator());
      form.add(emailField);

      TextFieldWithLabel<String> firstNameField = new TextFieldWithLabel<String>(
              "firstName",
              new PropertyModel<String>(newUserModel, User.FIELD_FIRST_NAME),
              new ResourceModel("first.name.field"),
              true);
      form.add(firstNameField);

      TextFieldWithLabel<String> lastNameField = new TextFieldWithLabel<String>(
              "lastName",
              new PropertyModel<String>(newUserModel, User.FIELD_LAST_NAME),
              new ResourceModel("last.name.field"),
              true);
      form.add(lastNameField);

      Form passwordForm = new Form("passwordForm");
      PasswordFieldWithLabel passwordField = new PasswordFieldWithLabel(
              "password.pass",
              passwordModel,
              new ResourceModel("password.pass"),
              true,
              true,
              StringValidator.maximumLength(Password.FIELD_PASSWORD_LENGTH));
      passwordField.setRequired(true);
      passwordForm.add(passwordField);

      PasswordFieldWithLabel confirmPasswordField = new PasswordFieldWithLabel(
              "password.confirmPass",
              confirmPasswordModel,
              new ResourceModel("password.confirmPass"),
              true,
              true,
              StringValidator.maximumLength(Password.FIELD_PASSWORD_LENGTH));
      confirmPasswordField.setRequired(true);
      passwordForm.add(confirmPasswordField);
      passwordForm.add(new UserPasswordValidator(passwordField.getField(), confirmPasswordField.getField()));
      form.add(passwordForm);

      AjaxSubmitLink registerLink = new AjaxSubmitLink("register", form)
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            onCreate(newUserModel.getObject(), passwordModel.getObject());
         }

         @Override
         protected void onError(AjaxRequestTarget target, Form<?> form)
         {
            super.onError(target, form);
            target.add(form);
         }
      };
      form.add(registerLink);
      form.setDefaultButton(registerLink);
   }

   protected abstract void onCreate(User user, String pass);
}
