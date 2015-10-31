package pl.ftt.domain.utils;

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
