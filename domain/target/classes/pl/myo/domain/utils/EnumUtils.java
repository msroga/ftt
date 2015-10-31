package pl.myo.domain.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class EnumUtils
{
   private EnumUtils()
   {
   }

   public static <E extends Enum<E>> List<E> exclude(Class<E> enumClass, E... exclude)
   {
      Iterator<E> iter = EnumSet.allOf(enumClass).iterator();
      List<E> enums = new ArrayList<E>();

      while (iter.hasNext())
      {
         E enm = iter.next();
         if (exclude == null || !contains(enm, exclude))
         {
            enums.add(enm);
         }
      }
      return enums;
   }

   private static <E extends Enum<E>> boolean contains(E enm, E... exclude)
   {
      for (E obj : exclude)
      {
         if (obj == enm)
         {
            return true;
         }
      }
      return false;
   }
}
