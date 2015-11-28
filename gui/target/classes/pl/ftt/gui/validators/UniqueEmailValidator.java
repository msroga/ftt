package pl.ftt.gui.validators;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import pl.ftt.domain.User;
import pl.ftt.service.IUserService;

/**
 * Created by Marek on 2015-11-28.
 */
public class UniqueEmailValidator implements IValidator<String>
{
   @SpringBean
   private IUserService userService;

   private IModel<User> model;

   public UniqueEmailValidator(IModel<User> model)
   {
      Injector.get().inject(this);
      this.model = model;
   }

   public UniqueEmailValidator()
   {
      this(null);
   }

   @Override
   public void validate(IValidatable<String> validatable)
   {
      String value = (String) validatable.getValue();
      User user = userService.getByEmail(value);
      if (user != null)
      {
         if (model != null)
         {
            String email = model.getObject().getEmail();
            if (!user.getEmail().equals(email))
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
      validatable.error(error);
   }
}
