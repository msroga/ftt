package pl.myo.core.component.portlet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import pl.myo.core.component.form.AbstractLabelComponent;
import pl.myo.domain.AbstractEntity;

public abstract class AbstractPortletView<T extends AbstractEntity> extends GenericPanel<T>
{
   public AbstractPortletView(String id, IModel<T> model)
   {
      super(id, model);
   }

   protected AjaxFallbackLink createEditLink(IModel<String> model)
   {
      AjaxFallbackLink editLink = new AjaxFallbackLink("editLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onModeChange(target);
         }
      };

      Label label = new Label("label", model);
      editLink.add(label);
      add(editLink);
      return editLink;
   }

   @Override
   protected void onModelChanged()
   {
      super.onModelChanged();
      visitChildren(AbstractLabelComponent.class, new IVisitor<AbstractLabelComponent, Void>()
      {
         @Override
         public void component(AbstractLabelComponent component, IVisit<Void> visit)
         {
            component.getField().modelChanged();
         }
      });
   }

   protected void rollback()
   {
      visitChildren(AbstractLabelComponent.class, new IVisitor<AbstractLabelComponent, Void>()
      {
         @Override
         public void component(AbstractLabelComponent component, IVisit<Void> visit)
         {
            if (component.getField() != null)
            {
               component.getField().modelChanged();
            }
         }
      });
   }

   public abstract void onModeChange(AjaxRequestTarget target);

   public abstract AjaxFallbackLink getEditLink();
}
