package pl.ftt.gui.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.ValidationError;

public class UserPasswordValidator extends AbstractFormValidator
{
   private static final long serialVersionUID = 8279433282782128278L;

   private final TextField password;

   private final TextField confirmPassword;

   public UserPasswordValidator(TextField password, TextField confirmPassword)
   {
      this.password = password;
      this.confirmPassword = confirmPassword;
   }

   @Override
   public FormComponent<?>[] getDependentFormComponents()
   {
      return new FormComponent[]
      {
            password, confirmPassword
      };
   }

   @Override
   public void validate(Form<?> form)
   {
      if (!StringUtils.equals(password.getInput(), confirmPassword.getInput()))
      {
         ValidationError error = new ValidationError();
         error.addKey("user.password.not.match");
         confirmPassword.error(error);
      }
   }
}
