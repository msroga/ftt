package pl.myo.gui.user.components;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import pl.myo.core.component.form.DropDownWithLabel;
import pl.myo.core.component.form.EnumDropDownWithLabel;
import pl.myo.core.component.form.PasswordFieldWithLabel;
import pl.myo.core.component.form.TextFieldWithLabel;
import pl.myo.core.component.portlet.AbstractEditablePanel;
import pl.myo.core.component.portlet.BootstrapViewLabel;
import pl.myo.core.component.portlet.GenderChoiceRenderer;
import pl.myo.core.component.portlet.GenderValuesFormatter;
import pl.myo.domain.Password;
import pl.myo.domain.User;
import pl.myo.domain.UserTypeEnum;
import pl.myo.gui.validators.SocialNumberValidator;
import pl.myo.gui.validators.UniqueLoginValidator;
import pl.myo.gui.validators.UserPasswordValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marek on 2015-05-27.
 */
public class UserEditablePanel extends AbstractEditablePanel<User>
{
   private final IModel<String> confirmPasswordModel = new Model<String>();

   public UserEditablePanel(String id, IModel<User> model, final IModel<String> passwordModel)
   {
      super(id, model);

      TextFieldWithLabel<String> loginField = new TextFieldWithLabel<String>("loginField",
              new PropertyModel(model, User.FIELD_LOGIN),
              new ResourceModel("user.login"), true);
      loginField.add(new StringValidator(4, User.FIELD_LOGIN_LENGTH));
      loginField.add(new UniqueLoginValidator());
      add(loginField);

      TextFieldWithLabel<String> nameField = new TextFieldWithLabel<String>("nameField",
              new PropertyModel(model, User.FIELD_NAME), new ResourceModel("user.name"), true);
      nameField.add(new StringValidator(0, User.FIELD_NAME_LENGTH));
      add(nameField);

      TextFieldWithLabel<String> surnameField = new TextFieldWithLabel<String>("surnameField",
              new PropertyModel(model, User.FIELD_SURNNAME), new ResourceModel("user.surname"), true);
      surnameField.add(new StringValidator(0, User.FIELD_SURNAME_LENGTH));
      add(surnameField);

      TextFieldWithLabel<String> emailField = new TextFieldWithLabel<String>("emailField",
              new PropertyModel(model, User.FIELD_EMAIL), new ResourceModel("user.email"), true);
      emailField.add(new StringValidator(0, User.FIELD_EMAIL_LENGTH));
      emailField.add(EmailAddressValidator.getInstance());
      add(emailField);

      TextFieldWithLabel<String> socialNumberField = new TextFieldWithLabel<String>("socialNumberField",
              new PropertyModel(model, User.FIELD_SOCIAL_NUMBER), new ResourceModel("user.social.number"), false);
      socialNumberField.add(SocialNumberValidator.getInstance());
      add(socialNumberField);

      EnumDropDownWithLabel<UserTypeEnum> typeField = new EnumDropDownWithLabel<UserTypeEnum>("typeField",
              new PropertyModel(model, User.FIELD_TYPE),
              new ResourceModel("user.type"), true, UserTypeEnum.class);
      add(typeField);

      List<Boolean> choices = new ArrayList<Boolean>();
      choices.add(Boolean.TRUE);
      choices.add(Boolean.FALSE);
      DropDownWithLabel<Boolean> genderField = new DropDownWithLabel<Boolean>("genderField",
              new PropertyModel<Boolean>(model, User.FIELD_GENDER), new ResourceModel("user.gender"), choices, false);
      genderField.getField().setChoiceRenderer(new GenderChoiceRenderer());
      add(genderField);

      WebMarkupContainer passwordContainer = new WebMarkupContainer("passwordContainer");
      add(passwordContainer);

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
      passwordContainer.add(passwordForm);

      Label passwordInformation = new Label("passwordInformation", new ResourceModel("do.not.fill.password"))
      {
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            setVisible(getModelObject().getId() != null);
         }
      };
      passwordContainer.add(passwordInformation);
   }
}
