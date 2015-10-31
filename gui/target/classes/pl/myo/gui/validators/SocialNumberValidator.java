package pl.myo.gui.validators;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import pl.myo.domain.utils.NormalizationUtils;

public class SocialNumberValidator implements IValidator<String>
{
   private static final SocialNumberValidator INSTANCE = new SocialNumberValidator();

   @Override
   public void validate(IValidatable<String> validatable)
   {
      String value = validatable.getValue();
      if (value == null || !isSocialNumberValid(value))
      {
         ValidationError error = new ValidationError(this);
         validatable.error(decorate(error, validatable));
      }
   }

   private boolean isSocialNumberValid(String socialNumber)
   {
      socialNumber = NormalizationUtils.normalizeString(socialNumber);

      if (socialNumber == null || socialNumber.length() != 11)
      {
         return false;
      }
      int[] weights =
              {
                      1, 3, 7, 9, 1, 3, 7, 9, 1, 3
              };
      String[] aSocialNumber = socialNumber.split("");
      try
      {
         int sum = 0;
         for (int i = 0; i < weights.length; i++)
         {
            sum += Integer.parseInt(aSocialNumber[i + 1]) * weights[i];
         }
         return (10 - (sum % 10)) == Integer.parseInt(aSocialNumber[11]);
      }
      catch (NumberFormatException e)
      {
         return false;
      }
   }

   protected ValidationError decorate(ValidationError error, IValidatable<String> validatable)
   {
      return error;
   }

   public static SocialNumberValidator getInstance()
   {
      return INSTANCE;
   }
}
