package pl.ftt.gui.formatter;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.ftt.core.scanner.IPackageScanner;

import java.io.Serializable;
import java.util.List;

public class FormatterScanner implements Serializable
{
   private static final long serialVersionUID = 1474562515549843994L;

   @SpringBean
   private IPackageScanner packageScanner;

   public FormatterScanner()
   {
      Injector.get().inject(this);
   }

   public void scanPackages(String... packagesToScan)
   {
      List<Class> classes = packageScanner.scanPackages(Formatter.class, packagesToScan);
      try
      {
         for (Class clazz : classes)
         {
            if (IFormatter.class.isAssignableFrom(clazz))
            {
               IFormatter formatter = (IFormatter) clazz.newInstance();
               Formatters.set(formatter.getTarget(), formatter);
            }
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException("unable to initialize formatters", e);
      }
   }
}
