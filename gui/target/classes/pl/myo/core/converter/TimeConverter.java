package pl.myo.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class TimeConverter extends AbstractConverter
{
   private static final String DEFAULT_FORMATTER_PATTERN = "HH:mm";

   private final IModel<DateTimeFormatter> formatterModel;

   public TimeConverter()
   {
      this(DEFAULT_FORMATTER_PATTERN);
   }

   public TimeConverter(final String pattern)
   {
      DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
      formatterModel = new LoadableDetachableModel<DateTimeFormatter>(formatter)
      {
         @Override
         protected DateTimeFormatter load()
         {
            return DateTimeFormat.forPattern(pattern);
         }
      };
   }

   @Override
   public Object convertToObject(String value, Locale locale) throws ConversionException
   {
      try
      {
         if (!StringUtils.isBlank(value))
         {
            DateTimeFormatter formatter = formatterModel.getObject();
            return LocalTime.parse(value, formatter);
         }
         else
         {
            return null;
         }
      }
      catch (Exception e)
      {
         throw newConversionException("Invalid time.", value, locale);
      }
   }

   @Override
   public String convertToString(Object value, Locale locale)
   {
      LocalTime localTime = (LocalTime) value;
      DateTimeFormatter formatter = formatterModel.getObject();
      return locale != null ? formatter.print(localTime) : null;
   }

   @Override
   protected Class getTargetType()
   {
      return LocalTime.class;
   }
}
