package pl.myo.core.notification;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

public final class Notification
{
   private static final String SUCCESS_TITLE = "success";

   private static final String INFO_TITLE = "info";

   private static final String ERROR_TITLE = "error";

   private static final String WARNING_TITLE = "warn";

   private static final String NOTIFY_TIMEOUT = "5000";

   private Notification()
   {
   }

   public static String getType(NotificationEnum type)
   {
      switch ( type)
      {
         case SUCCESS:
            return SUCCESS_TITLE;

         case ERROR:
            return ERROR_TITLE;

         case INFO:
            return INFO_TITLE;

         case WARNING:
            return WARNING_TITLE;

         default:
            return null;
      }
   }

   public static String getJavaScript(String message, NotificationEnum type)
   {
      StringBuilder javaScript = new StringBuilder();
      javaScript.append("$.notify('" + message + "', '"+ getType(type) +"', {hideDuration: 20}");
      javaScript.append(");");
      return javaScript.toString();
   }

   private static void message(String message, NotificationEnum type)
   {
      AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
      if (target != null)
      {
         String javaScript = getJavaScript(message, type);
         target.appendJavaScript(javaScript);
      }
      else
      {
         throw new RuntimeException("ajax request not found!");
      }
   }

   public static void success(String message)
   {
      message(message, NotificationEnum.SUCCESS);
   }

   public static void error(String message)
   {
      message(message, NotificationEnum.ERROR);
   }

   public static void info(String message)
   {
      message(message, NotificationEnum.INFO);
   }

   public static void warn(String message)
   {
      message(message, NotificationEnum.WARNING);
   }

//   private static String getString(String key)
//   {
//      return Application.get().getResourceSettings().getLocalizer().getString(key, null);
//   }
}
