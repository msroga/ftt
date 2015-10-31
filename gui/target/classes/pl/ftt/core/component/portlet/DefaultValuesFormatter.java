package pl.ftt.core.component.portlet;

import org.apache.wicket.Component;
import pl.ftt.gui.formatter.Formatters;

public class DefaultValuesFormatter implements IValuesFormatter
{
   private static final long serialVersionUID = 5401125377264766971L;

   private final String separator;

   private final Component component;

   public DefaultValuesFormatter(Component component, String separator)
   {
      this.separator = separator;
      this.component = component;
   }

   public DefaultValuesFormatter(Component component)
   {
      this(component, " ");
   }

   public DefaultValuesFormatter(String separator)
   {
      this(null, separator);
   }

   public DefaultValuesFormatter()
   {
      this(" ");
   }

   @Override
   public String format(Object[] objects)
   {
      StringBuilder sb = new StringBuilder();
      int i = 0;
      for (Object object : objects)
      {
         if (object != null)
         {
            sb.append(Formatters.format(object, component));
         }
         if (i + 1 < objects.length)
         {
            sb.append(separator);
         }
      }
      return sb.toString();
   }
}
