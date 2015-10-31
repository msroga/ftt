package pl.ftt.gui.formatter.impl;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.joda.time.DateTime;
import pl.ftt.core.converter.DateConverter;
import pl.ftt.gui.formatter.Formatter;
import pl.ftt.gui.formatter.IFormatter;

@Formatter
public class DateTimeFormatter implements IFormatter<DateTime>
{
   private static final DateConverter converter = DateConverter.forClass(DateTime.class, "dd.MM.yy' 'HH:mm");

   @Override
   public String format(DateTime obj, Component component)
   {
      return obj != null ? converter.convertToString(obj, Session.get().getLocale()) : null;
   }

   @Override
   public Class<DateTime> getTarget()
   {
      return DateTime.class;
   }

}
