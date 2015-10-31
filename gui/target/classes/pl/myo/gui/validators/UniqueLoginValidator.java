package pl.myo.gui.validators;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import pl.myo.domain.User;
import pl.myo.service.IUserService;

public class UniqueLoginValidator implements IValidator<String>
{
   @SpringBean
   private IUserService userService;

   private IModel<User> model;

   public UniqueLoginValidator()
   {
      this(null);
   }

   /**
    * @param model if not NULL check does validated login is actual edited user otherway do not check
    */
   public UniqueLoginValidator(IModel<User> model)
   {
      Injector.get().inject(this);
      this.model = model;
   }

   @Override
   public void validate(IValidatable<String> validatable)
   {
      String value = (String) validatable.getValue();
      User user = userService.getByLogin(value);

      if (user != null)
      {
         if (model != null)
         {
            String login = model.getObject().getLogin();
            if (!user.getLogin().equals(login))
            {
               putError(validatable);
            }
         }
         else
         {
            putError(validatable);
         }
      }
   }

   private void putError(IValidatable<String> validatable)
   {
      ValidationError error = new ValidationError(this);
      validatable.error(decorate(error, validatable));
   }

   protected ValidationError decorate(ValidationError error, IValidatable<String> validatable)
   {
      return error;
   }
}
