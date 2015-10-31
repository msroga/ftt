package pl.myo.core.component.table;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public abstract class FilterDecorator implements Serializable
{
   private static final long serialVersionUID = 2426839135610711760L;

   public void decorate(final Component filter, IModel<String> placeholder)
   {
      if (!(filter instanceof AbstractTextComponent))
      {
         filter.add(new OnChangeAjaxBehavior()
         {
            private static final long serialVersionUID = -5038349502569456291L;

            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
               FilterDecorator.this.onUpdate(target);
            }

            /*@Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
               super.updateAjaxAttributes(attributes);

               attributes.setThrottlingSettings(new ThrottlingSettings(filter.getId(), Duration.NONE, true));
               attributes.setChannel(new AjaxChannel(filter.getId(), AjaxChannel.Type.DROP));
            }*/
         });
      }

      if (placeholder != null)
      {
         filter.add(new AttributeModifier("placeholder", placeholder));
      }
   }

   protected abstract void onUpdate(AjaxRequestTarget target);
}
