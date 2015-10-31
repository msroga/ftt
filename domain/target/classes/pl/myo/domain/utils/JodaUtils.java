package pl.myo.domain.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.Date;
import java.util.Locale;

public final class JodaUtils
{
   private static final PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
         .appendYears()
         .appendSuffix(" years, ")
         .appendMonths()
         .appendSuffix(" months, ")
         .appendWeeks()
         .appendSuffix(" weeks, ")
         .appendDays()
         .appendSuffix(" days, ")
         .appendHours()
         .appendSuffix(" hours, ")
         .appendMinutes()
         .appendSuffix(" minutes, ")
         .appendSeconds()
         .appendSuffix(" seconds, ")
         .appendMillis()
         .appendSuffix(" milis")
         .printZeroNever()
         .toFormatter();

   private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormat
         .forPattern("dd.MM.yyyy'T'HH:mm:ss.SSSZ");

   private static final DateTimeFormatter DEFAULT_LOCAL_DATE_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");

   private JodaUtils()
   {

   }

   public static String timeSpanToString(long duration)
   {
      Period period = new Period(duration);
      return periodFormatter.print(period);
   }

   public static String print(DateTimeFormatter formatter, DateTime dateTime)
   {
      return dateTime != null ? formatter.print(dateTime) : null;
   }

   public static String print(DateTimeFormatter formatter, LocalDate localDate)
   {
      return localDate != null ? formatter.print(localDate) : null;
   }

   public static String printDateTime(DateTime dateTime)
   {
      return print(DEFAULT_DATE_TIME_FORMATTER, dateTime);
   }

   public static DateTime parseDateTime(String str)
   {
      try
      {
         if (!StringUtils.isBlank(str))
         {
            return DEFAULT_DATE_TIME_FORMATTER.parseDateTime(str);
         }
      }
      catch (IllegalArgumentException e)
      {
         // noop
      }
      return null;
   }

   public static String printLocalDate(LocalDate localDate)
   {
      return print(DEFAULT_LOCAL_DATE_FORMATTER, localDate);
   }

   public static LocalDate parseLocalDate(String str)
   {
      try
      {
         if (!StringUtils.isBlank(str))
         {
            return DEFAULT_LOCAL_DATE_FORMATTER.parseLocalDate(str);
         }
      }
      catch (IllegalArgumentException e)
      {
         // noop
      }
      return null;
   }

   public static DateTime getBeginOfDay(DateTime dateTime)
   {
      return dateTime.withTime(0, 0, 0, 0);
   }

   public static DateTime getEndOfDay(DateTime dateTime)
   {
      return dateTime.withTime(23, 59, 59, 999);
   }

   public static DateTimeFormatter getFormat(Locale locale, Class<?> targetClass, String pattern, DateTimeZone zone)
   {
      DateTimeFormatter formatter;
      if (isJodaDateTimeFormat(pattern))
      {
         String p = pattern;
         if (LocalDate.class.isAssignableFrom(targetClass))
         {
            p = new String(new char[]
            {
                  p.charAt(0), '-'
            });
         }
         formatter = DateTimeFormat.forStyle(p).withLocale(locale);
      }
      else
      {
         formatter = DateTimeFormat.forPattern(pattern).withLocale(locale);
      }

      return formatter.withZone(zone);
   }

   public static DateTimeFormatter getFormat(Locale locale, Class<?> targetClass, String pattern)
   {
      return getFormat(locale, targetClass, pattern, getTimeZone());
   }

   public static boolean isJodaDateTimeFormat(String pattern)
   {
      final String DATETIME_STYLE_CHARSET = "SMLF-";
      if (pattern.length() == 2 && DATETIME_STYLE_CHARSET.indexOf(pattern.charAt(0)) >= 0
            && DATETIME_STYLE_CHARSET.indexOf(pattern.charAt(1)) >= 0)
      {
         return true;
      }
      return false;
   }

   public static DateTimeZone getTimeZone()
   {
      return DateTimeZone.getDefault();
   }

   public static Object convertToObject(DateTime dateTime, Class<?> targetClass)
   {
      if (DateTime.class.isAssignableFrom(targetClass))
      {
         return dateTime;
      }
      else if (LocalDate.class.isAssignableFrom(targetClass))
      {
         return new LocalDate(dateTime.getMillis());

      }
      else if (Date.class.isAssignableFrom(targetClass))
      {
         return dateTime.toDate();
      }
      else
      {
         throw new IllegalStateException("Target class is not a valid date type: " + targetClass.getName());
      }
   }

   public static DateTime convertToDateTime(Object object)
   {
      if (object instanceof DateTime)
      {
         return (DateTime) object;
      }
      else if (object instanceof LocalDate)
      {
         return ((LocalDate) object).toDateTimeAtStartOfDay();

      }
      else if (object instanceof Date)
      {
         return new DateTime(object);
      }
      else
      {
         throw new IllegalStateException("Target class is not a valid date type: " + object.getClass());
      }
   }
}
