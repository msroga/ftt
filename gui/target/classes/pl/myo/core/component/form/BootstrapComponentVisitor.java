package pl.myo.core.component.form;

import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

public class BootstrapComponentVisitor implements IVisitor<AbstractLabelComponent<?>, Void>
{
   private AjaxRequestAttributes attributes;

   public BootstrapComponentVisitor(AjaxRequestAttributes attributes)
   {
      this.attributes = attributes;
   }

   @Override
   public void component(final AbstractLabelComponent<?> boostrapComponent, IVisit<Void> visit)
   {
      if (boostrapComponent.isVisible())
      {
         if (boostrapComponent.getBeforeScript() != null)
         {
            attributes.getAjaxCallListeners().add(new AjaxCallListener()
            {
               {
                  onBefore(boostrapComponent.getBeforeScript());
               }
            });
         }
         if (boostrapComponent.getAfterScript() != null)
         {
            attributes.getAjaxCallListeners().add(new AjaxCallListener()
            {
               {
                  onAfter(boostrapComponent.getAfterScript());
               }
            });
         }
      }
   }
}
