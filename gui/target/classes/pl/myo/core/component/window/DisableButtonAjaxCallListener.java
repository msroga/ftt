package pl.myo.core.component.window;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;

public class DisableButtonAjaxCallListener extends AjaxCallListener
{
   private final String domId;

   public DisableButtonAjaxCallListener(String domId)
   {
      this.domId = domId;
   }

   @Override
   public CharSequence getBeforeHandler(Component component)
   {
      return "$('#" + domId + "').addClass('disabled'); $('#" + domId + "').attr('disabled', 'disabled');";
   }
}
