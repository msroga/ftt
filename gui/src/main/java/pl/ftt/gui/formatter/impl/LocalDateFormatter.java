package pl.ftt.gui.formatter.impl;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.joda.time.LocalDate;
import pl.ftt.core.converter.DateConverter;
import pl.ftt.gui.formatter.Formatter;
import pl.ftt.gui.formatter.IFormatter;

@Formatter
public class LocalDateFormatter implements IFormatter<LocalDate>
{
   public static final String DEFAULT_LOCAL_DATE_FORMAT = "dd.MM.yy";

   private static final DateConverter converter = DateConverter.forClass(LocalDate.class, DEFAULT_LOCAL_DATE_FORMAT);

   @Override
   public String format(LocalDate obj, Component component)
   {
      return obj != null ? converter.convertToString(obj, Session.get().getLocale()) : null;
   }

   @Override
   public Class<LocalDate> getTarget()
   {
      return LocalDate.class;
   }
}
