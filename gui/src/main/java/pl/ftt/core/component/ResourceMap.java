package pl.ftt.core.component;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.model.Model;

import java.util.HashMap;
import java.util.Locale;

public class ResourceMap extends HashMap<String, String>
{
   public ResourceMap(Component component, Locale locale, String... keys)
   {
      if (keys != null)
      {
         for (String key : keys)
         {
            put(key, getString(key, component, locale));
         }
      }
   }

   private static String getString(String key, Component component, Locale locale)
   {
      return Application.get().getResourceSettings().getLocalizer().getString(key, component, null, locale, null, new Model<String>());
   }
}
