package pl.ftt.core.converter;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

public class BigDecimalConverter implements IConverter<BigDecimal>
{
   private static final long serialVersionUID = 5068073764484944500L;

   private final String pattern;

   public BigDecimalConverter(String pattern)
   {
      this.pattern = pattern;
   }

   public BigDecimalConverter()
   {
      this(null);
   }

   protected DecimalFormat getDecimalFormat(Locale locale)
   {
      DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
      if (pattern != null)
      {
         decimalFormat.applyPattern(pattern);
      }
      decimalFormat.setPositivePrefix("");
      decimalFormat.setNegativePrefix("-");
      decimalFormat.setParseBigDecimal(true);
      return decimalFormat;
   }

   @Override
   public BigDecimal convertToObject(String value, Locale locale)
   {
      try
      {
         return value != null ? (BigDecimal) getDecimalFormat(locale).parseObject(value) : null;
      }
      catch (Exception e)
      {
         throw new ConversionException("value " + value + " is not valid BigDecimal");
      }
   }

   @Override
   public String convertToString(BigDecimal value, Locale locale)
   {
      return value != null ? getDecimalFormat(locale).format(value) : null;

   }
}
