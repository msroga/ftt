package pl.ftt.gui.formatter;

import org.apache.wicket.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Formatters
{
   private static ConcurrentMap<Class, IFormatter> formatters = new ConcurrentHashMap<Class, IFormatter>();

   private Formatters()
   {

   }

   public static <T> IFormatter<T> get(Class<T> clazz)
   {
      return formatters.get(clazz);
   }

   public static String format(Object obj, Component component)
   {
      if (obj != null)
      {
         IFormatter formatter;
         if (obj instanceof Enum)
         {
            formatter = EnumFormatter.getInstance();
         }
         else
         {
            formatter = get(obj.getClass());
         }
         return formatter != null ? formatter.format(obj, component) : obj.toString();
      }
      return null;
   }

   public static <T> void set(Class<T> clazz, IFormatter<T> formatter)
   {
      formatters.put(clazz, formatter);
   }
}
