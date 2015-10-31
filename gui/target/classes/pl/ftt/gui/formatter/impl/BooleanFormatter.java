package pl.ftt.gui.formatter.impl;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.util.string.Strings;
import pl.ftt.gui.formatter.Formatter;
import pl.ftt.gui.formatter.IFormatter;

@Formatter
public class BooleanFormatter implements IFormatter<Boolean>
{
   @Override
   public String format(Boolean object, Component component)
   {
      String key = Boolean.class.getSimpleName() + '.' + object;
      String value = Application.get().getResourceSettings().getLocalizer().getString(key, component);
      return Strings.escapeMarkup(value).toString();
   }

   @Override
   public Class<Boolean> getTarget()
   {
      return Boolean.class;
   }
}
