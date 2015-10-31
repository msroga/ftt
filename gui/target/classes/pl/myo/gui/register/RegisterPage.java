package pl.myo.gui.register;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.annotation.mount.MountPath;
import pl.myo.core.component.form.DropDownWithLabel;
import pl.myo.core.component.form.EnumDropDownWithLabel;
import pl.myo.core.component.form.PasswordFieldWithLabel;
import pl.myo.core.component.form.TextFieldWithLabel;
import pl.myo.core.component.portlet.GenderChoiceRenderer;
import pl.myo.core.mappings.MyoApiMappings;
import pl.myo.core.pages.AbstractPage;
import pl.myo.domain.Password;
import pl.myo.domain.User;
import pl.myo.domain.UserTypeEnum;
import pl.myo.gui.login.LoginPage;
import pl.myo.gui.validators.SocialNumberValidator;
import pl.myo.gui.validators.UniqueLoginValidator;
import pl.myo.gui.validators.UserPasswordValidator;
import pl.myo.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-06-13.
 */
@MountPath(MyoApiMappings.REGISTER_PAGE)
public class RegisterPage extends AbstractPage
{
   private final IModel<User> model = new Model<>();

   private final IModel<String> passwordModel = new Model<>();

   private final IModel<String> confirmPasswordModel = new Model<>();

   @SpringBean
   private IUserService userService;

   public RegisterPage()
   {
      User user = new User();
      user.setType(UserTypeEnum.USER);
      model.setObject(user);
      Form form = new Form("form");
      add(form);

      TextFieldWithLabel<String> loginField = new TextFieldWithLabel<String>("loginField",
              new PropertyModel(model, User.FIELD_LOGIN),
              new ResourceModel("user.login"), true);
      loginField.add(new StringValidator(4, User.FIELD_LOGIN_LENGTH));
      loginField.add(new UniqueLoginValidator());
      form.add(loginField);

      TextFieldWithLabel<String> nameField = new TextFieldWithLabel<String>("nameField",
              new PropertyModel(model, User.FIELD_NAME), new ResourceModel("user.name"), true);
      nameField.add(new StringValidator(0, User.FIELD_NAME_LENGTH));
      form.add(nameField);

      TextFieldWithLabel<String> surnameField = new TextFieldWithLabel<String>("surnameField",
              new PropertyModel(model, User.FIELD_SURNNAME), new ResourceModel("user.surname"), true);
      surnameField.add(new StringValidator(0, User.FIELD_SURNAME_LENGTH));
      form.add(surnameField);

      TextFieldWithLabel<String> emailField = new TextFieldWithLabel<String>("emailField",
              new PropertyModel(model, User.FIELD_EMAIL), new ResourceModel("user.email"), true);
      emailField.add(new StringValidator(0, User.FIELD_EMAIL_LENGTH));
      emailField.add(EmailAddressValidator.getInstance());
      form.add(emailField);

      TextFieldWithLabel<String> socialNumberField = new TextFieldWithLabel<String>("socialNumberField",
              new PropertyModel(model, User.FIELD_SOCIAL_NUMBER), new ResourceModel("user.social.number"), false);
      socialNumberField.add(SocialNumberValidator.getInstance());
      form.add(socialNumberField);

      List<Boolean> choices = new ArrayList<Boolean>();
      choices.add(Boolean.TRUE);
      choices.add(Boolean.FALSE);
      DropDownWithLabel<Boolean> genderField = new DropDownWithLabel<Boolean>("genderField",
              new PropertyModel<Boolean>(model, User.FIELD_GENDER), new ResourceModel("user.gender"), choices, false);
      genderField.getField().setChoiceRenderer(new GenderChoiceRenderer());
      form.add(genderField);

      WebMarkupContainer passwordContainer = new WebMarkupContainer("passwordContainer");
      add(passwordContainer);

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
      passwordContainer.add(passwordForm);
      form.add(passwordContainer);

      AjaxSubmitLink submitLink = new AjaxSubmitLink("submitLink")
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            userService.save(model.getObject(), passwordModel.getObject());
            setResponsePage(LoginPage.class);
         }

         @Override
         protected void onError(AjaxRequestTarget target, Form<?> form)
         {
            super.onError(target, form);
            target.add(form);
         }
      };
      form.add(submitLink);
      form.setDefaultButton(submitLink);
      form.setOutputMarkupId(true);
   }
}
