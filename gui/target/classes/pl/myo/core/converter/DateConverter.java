package pl.myo.core.converter;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.wicket.Session;
import org.apache.wicket.core.request.ClientInfo;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.joda.time.base.AbstractPartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.myo.domain.utils.JodaUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DateConverter extends AbstractConverter
{
   private static final long serialVersionUID = -7849650741293457275L;

   protected final static Logger log = LoggerFactory.getLogger(DateConverter.class);

   private final DateOptions options;

   private static ConcurrentMap<DateOptions, DateConverter> converters = new ConcurrentHashMap<DateOptions, DateConverter>();

   private DateConverter(Class<?> targetClass, String pattern, boolean applyTimeZoneDifference)
   {
      if (targetClass != null && !DateTime.class.isAssignableFrom(targetClass)
            && !LocalDate.class.isAssignableFrom(targetClass) && !Date.class.isAssignableFrom(targetClass))
      {
         throw new IllegalArgumentException("Target class is not a valid date type: " + targetClass.getName());
      }

      this.options = new DateOptions(targetClass, pattern, applyTimeZoneDifference);
   }

   public static DateConverter forClass(Class<?> targetClass)
   {
      return forClass(targetClass, "SS", true);
   }

   public static DateConverter forClass(Class<?> targetClass, String pattern)
   {
      return forClass(targetClass, pattern, true);
   }

   public static DateConverter forClass(Class<?> targetClass, boolean applyTimeZoneDifference)
   {
      return forClass(targetClass, "SS", applyTimeZoneDifference);
   }

   public static DateConverter forClass(Class<?> targetClass, String pattern, boolean applyTimeZoneDifference)
   {
      DateOptions options = new DateOptions(targetClass, pattern, applyTimeZoneDifference);
      DateConverter converter = converters.get(options);
      if (converter == null)
      {
         converter = new DateConverter(targetClass, pattern, applyTimeZoneDifference);
         converters.put(options, converter);
      }
      return converter;
   }

   @Override
   public Object convertToObject(String value, Locale locale)
   {
      if (value == null || value.length() == 0)
      {
         return null;
      }

      if (options.getTargetClass() == null)
      {
         return null;
      }

      try
      {
         DateTimeFormatter format = JodaUtils.getFormat(locale, options.getTargetClass(), options.getPattern());

         MutableDateTime mdt = new MutableDateTime();
         if (getApplyTimeZoneDifference())
         {
            TimeZone zone = getClientTimeZone();
            // instantiate now/ current time
            if (zone != null)
            {
               // set time zone for client
               format = format.withZone(DateTimeZone.forTimeZone(zone));
               mdt.setZone(DateTimeZone.forTimeZone(zone));
            }
            // parse date retaining the time of the submission
            if (format.parseInto(mdt, value, 0) < 0)
            {
               throw newConversionException("Invalid date.", value, locale);
            }
            // apply the server time zone to the parsed value
            mdt.setZone(JodaUtils.getTimeZone());
         }
         else
         {
            if (format.parseInto(mdt, value, 0) < 0)
            {
               throw newConversionException("Invalid date.", value, locale);
            }
         }
         return JodaUtils.convertToObject(mdt.toDateTime(), options.getTargetClass());
      }
      catch (ConversionException e)
      {
         throw e;
      }
      catch (RuntimeException e)
      {
         throw newConversionException("Invalid date.", value, locale);
      }
   }

   @Override
   public String convertToString(Object value, Locale locale)
   {
      try
      {
         DateTime dt = JodaUtils.convertToDateTime(value);
         DateTimeFormatter format = JodaUtils.getFormat(locale, options.getTargetClass(), options.getPattern());

         if (getApplyTimeZoneDifference())
         {
            TimeZone zone = getClientTimeZone();
            if (zone != null)
            {
               // apply time zone to formatter
               format = format.withZone(DateTimeZone.forTimeZone(zone));
            }
         }
         return format.print(dt);
      }
      catch (RuntimeException e)
      {
         log.error(e.getMessage());
         throw newConversionException("Invalid date.", value, locale);
      }
   }

   public boolean getApplyTimeZoneDifference()
   {
      if (options.getTargetClass() != null && AbstractPartial.class.isAssignableFrom(options.getTargetClass()))
      {
         return false;
      }
      return options.isApplyTimeZoneDifference();
   }

   public final String getDatePattern(Locale locale)
   {
      String str = this.options.getPattern();
      if (JodaUtils.isJodaDateTimeFormat(str))
      {
         str = DateTimeFormat.patternForStyle(this.options.getPattern(), locale);
      }
      return str;
   }

   public final String getJQueryDatePattern(Locale locale)
   {
      String str = getDatePattern(locale);

      str = str.replaceAll("yy", "y");
      str = str.replaceAll("EEEE", "DD");
      if (str.contains("MMMM"))
      {
         str = str.replaceAll("MMMM", "MM");
      }
      else if (str.contains("M"))
      {
         str = str.replaceAll("M", "m");
      }
      else if (str.contains("m"))
      {
         str = str.replaceAll("m", "M");
      }
      return str;
   }

   protected TimeZone getClientTimeZone()
   {
      ClientInfo info = Session.get().getClientInfo();
      if (info instanceof WebClientInfo)
      {
         return ((WebClientInfo) info).getProperties().getTimeZone();
      }
      return null;
   }

   @Override
   protected Class<?> getTargetType()
   {
      return options.getTargetClass();
   }

   public static class DateOptions implements Serializable
   {
      private static final long serialVersionUID = -7422288841861233491L;

      private final Class<?> targetClass;

      private final String pattern;

      private final boolean applyTimeZoneDifference;

      public DateOptions(Class<?> targetClass, String pattern, boolean applyTimeZoneDifference)
      {
         this.targetClass = targetClass;
         this.pattern = pattern;
         this.applyTimeZoneDifference = applyTimeZoneDifference;
      }

      public Class<?> getTargetClass()
      {
         return targetClass;
      }

      public String getPattern()
      {
         return pattern;
      }

      public boolean isApplyTimeZoneDifference()
      {
         return applyTimeZoneDifference;
      }

      @Override
      public int hashCode()
      {
         return new HashCodeBuilder()
               .append(targetClass != null ? targetClass.getName() : "")
               .append(pattern)
               .append(applyTimeZoneDifference)
               .toHashCode();
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj != null && obj.getClass().equals(getClass()))
         {
            DateOptions options = (DateOptions) obj;
            return options.targetClass.equals(targetClass) && options.pattern.equals(pattern)
                  && options.applyTimeZoneDifference == applyTimeZoneDifference;
         }
         return super.equals(obj);
      }
   }

   public DateOptions getOptions()
   {
      return options;
   }
}