package pl.myo.core.component.window;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.Random;

public abstract class AbstractWindow extends Panel
{
   private static final Random RANDOM = new Random();

   protected final String domId = "window" + RANDOM.nextInt();

   protected final WebMarkupContainer container;

   protected AjaxLink closeLink;

   protected final Label headerLabel;

   protected boolean opening;

   public AbstractWindow(String id, IModel<String> header)
   {
      super(id);
      setOutputMarkupId(true);
      setOutputMarkupPlaceholderTag(true);

      container = new WebMarkupContainer("container");
      container.setOutputMarkupId(true);
      container.setMarkupId(domId);
      container.add(AttributeModifier.replace("aria-labelledby", domId + "label"));

      headerLabel = new Label("headerLabel", header);
      headerLabel.setOutputMarkupId(true);
      headerLabel.setMarkupId(domId + "label");

      container.add(headerLabel);
      container.add(newCancelButton("closeButton"));

      add(container);
      setVisible(false);
   }

   public void setHeader(IModel<String> header)
   {
      headerLabel.setDefaultModel(header);
   }

   protected AjaxLink newCancelButton(String id)
   {
      AjaxLink link = new AjaxLink(id)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onCancel(target);
            AbstractWindow.this.hide(target);
         }
      };
      return link;
   }

   public void onCancel(AjaxRequestTarget target)
   {

   }

   public void hide(AjaxRequestTarget target)
   {
      setVisible(false);
      target.appendJavaScript("$('#" + domId + "').modal('hide');");
      target.appendJavaScript("$('.modal-backdrop').remove();");
   }

   public void show(AjaxRequestTarget target)
   {
      opening = true;
      setVisible(true);
      target.add(AbstractWindow.this);
      target.appendJavaScript("$('#" + domId + "').modal('show');");
   }

   public String getDomId()
   {
      return domId;
   }

   public boolean isOpening()
   {
      return opening;
   }

   @Override
   protected void onAfterRender()
   {
      super.onAfterRender();
      opening = false;
   }
}
