package pl.myo.core.component.portlet;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class GenderChoiceRenderer implements IChoiceRenderer<Boolean>
{
   @Override
   public Object getDisplayValue(Boolean object)
   {
      return getGenderName(object);
   }

   @Override
   public String getIdValue(Boolean object, int index)
   {
      return object.toString();
   }

   protected String getGenderName(Boolean b)
   {
      return Boolean.TRUE.equals(b) ? getString("gender.male") : getString("gender.female");
   }

   private static String getString(String key)
   {
      return Application.get().getResourceSettings().getLocalizer().getString(key, null);
   }
}
