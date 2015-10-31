package pl.ftt.core.component.portlet;

import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.domain.AbstractEntity;

public abstract class PortletView<T extends AbstractEntity> extends AbstractPortletView<T>
{
   private final AjaxFallbackLink editLink;

   protected final WebMarkupContainer editLinkContainer;

   public PortletView(String id, IModel<T> model)
   {
      super(id, model);
      editLink = createEditLink(new ResourceModel("link.edit"));
      editLinkContainer = new WebMarkupContainer("editLinkContainer")
      {
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            setVisible(editLink.isVisible());
         }
      };
      editLinkContainer.add(editLink);
      add(editLinkContainer);

      Panel readOnlyPanel = getReadOnlyPanel("readOnlyPanel", getModel());
      add(readOnlyPanel);
   }

   @Override
   public AjaxFallbackLink getEditLink()
   {
      return editLink;
   }

   protected abstract AbstractReadOnlyPanel<T> getReadOnlyPanel(String id, IModel<T> model);
}
