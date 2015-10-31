package pl.ftt.gui.formatter;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.string.Strings;

public class EnumFormatter implements IFormatter<Enum>
{
   private static final EnumFormatter INSTANCE = new EnumFormatter();

   private EnumFormatter()
   {
   }

   public static EnumFormatter getInstance()
   {
      return INSTANCE;
   }

   protected String resourceKey(Enum object)
   {
      return Classes.simpleName(object.getDeclaringClass()) + '.' + object.name();
   }

   protected CharSequence postprocess(String value)
   {
      return Strings.escapeMarkup(value);
   }

   @Override
   public String format(Enum object, Component component)
   {
      String key = resourceKey(object);
      String value = Application.get().getResourceSettings().getLocalizer().getString(key, component);
      return postprocess(value).toString();
   }

   @Override
   public Class<Enum> getTarget()
   {
      return Enum.class;
   }
}
