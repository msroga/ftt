package pl.myo.domain.utils;

import org.apache.commons.lang3.StringUtils;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.regex.Pattern;

public class NormalizationUtils
{
   private NormalizationUtils()
   {
   }

   public static String normalizeString(String string)
   {
      if (string != null)
      {
         string = string.replaceAll("-", "").replaceAll(" ", "").replace("_","");
      }
      return string;
   }

}
