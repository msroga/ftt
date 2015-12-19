package pl.ftt.gui.formatter.impl;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.joda.time.LocalTime;
import pl.ftt.core.converter.TimeConverter;
import pl.ftt.gui.formatter.Formatter;
import pl.ftt.gui.formatter.IFormatter;

/**
 * Created by Marek on 2015-12-19.
 */
@Formatter
public class LocalTimeFormatter implements IFormatter<LocalTime>
{
   private static final TimeConverter converter = new TimeConverter();

   @Override
   public String format(LocalTime obj, Component component)
   {
      return obj != null ? converter.convertToString(obj, Session.get().getLocale()) : null;
   }

   @Override
   public Class<LocalTime> getTarget()
   {
      return LocalTime.class;
   }
}
