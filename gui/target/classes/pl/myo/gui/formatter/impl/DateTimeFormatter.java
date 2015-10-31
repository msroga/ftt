package pl.myo.gui.formatter.impl;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.joda.time.DateTime;
import pl.myo.core.converter.DateConverter;
import pl.myo.gui.formatter.Formatter;
import pl.myo.gui.formatter.IFormatter;

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
