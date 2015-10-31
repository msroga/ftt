package pl.ftt.core.component.portlet;

import org.apache.wicket.Application;

/**
 * User: marek
 * Date: 08.05.14
 */
public class GenderValuesFormatter implements IValuesFormatter
{
   public GenderValuesFormatter()
   {
   }

   @Override
   public String format(Object[] objects)
   {
      boolean model = (Boolean) objects[0];
      String key = model ? "gender.male" : "gender.female";
      return getString(key);
   }

   private static String getString(String key)
   {
      return Application.get().getResourceSettings().getLocalizer().getString(key, null);
   }
}
