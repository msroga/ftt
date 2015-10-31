package pl.myo.core.component;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import pl.myo.gui.formatter.Formatters;

public class ConfirmationCallListener extends AjaxCallListener
{
   private static final long serialVersionUID = 6800889134407321820L;

   public ConfirmationCallListener(String msg, Object... params)
   {
      this(msg, null, params);
   }

   public ConfirmationCallListener(String msg, Component component, Object... params)
   {
      super();
      onPrecondition("return confirm('" + getMessage(msg, component, params) + "');");
   }

   protected String getMessage(String msg, Component component, Object... params)
   {
      if (params == null || params.length == 0)
      {
         return msg;
      }

      Object[] formatted = new Object[params.length];
      int i = 0;
      for (Object object : params)
      {
         if (object instanceof String)
         {
            formatted[i] = object;
         }
         else
         {
            formatted[i] = Formatters.format(object, component);
         }
         i++;
      }
      return format(msg, formatted);
   }

   protected String format(String msg, final Object... params)
   {
      msg = msg.replaceAll("\\{\\}", "%s");
      return StringEscapeUtils.escapeEcmaScript(String.format(msg, params));
   }
}
