package pl.myo.core.component.window;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import pl.myo.core.component.form.AbstractLabelComponent;
import pl.myo.core.component.form.BootstrapComponentVisitor;

public abstract class DefaultWindow extends AbstractWindow
{
   protected Form form;

   protected AjaxLink cancelButton;

   protected AjaxButton submitButton;

   public DefaultWindow(String id, IModel<String> header)
   {
      super(id, header);

      form = new Form("form");
      form.setEnabled(false);

      cancelButton = newCancelButton("cancelButton");
      form.add(cancelButton);

      submitButton = newSubmitButton("submitButton");
      form.add(submitButton);

      container.add(form);
   }

   protected AjaxButton newSubmitButton(String id)
   {
      AjaxButton link = new IndicatingAjaxButton(id, form)
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            DefaultWindow.this.onSubmit(target);
            hide(target);
         }

         @Override
         protected void onError(AjaxRequestTarget target, Form<?> form)
         {
            target.add(form);
            DefaultWindow.this.onError(target);
         }

         @Override
         protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
         {
            super.updateAjaxAttributes(attributes);
            attributes.getAjaxCallListeners().add(new DisableButtonAjaxCallListener("submit-" + domId));
            form.visitChildren(AbstractLabelComponent.class, new BootstrapComponentVisitor(attributes));
         }
      };
      form.setDefaultButton(link);
      link.setOutputMarkupId(true);
      link.setMarkupId("submit-" + domId);
      return link;
   }

   public void hide(AjaxRequestTarget target)
   {
      super.hide(target);
      form.setEnabled(false);
   }

   public void show(AjaxRequestTarget target)
   {
      form.setEnabled(true);
      Component component = getFocusComponent();
      if (component != null)
      {
         if (component instanceof AbstractLabelComponent)
         {
            component = ((AbstractLabelComponent) component).getField();
         }
         target.appendJavaScript("$('#" + domId + "').unbind('shown');");
         target.appendJavaScript("$('#" + domId + "').on('shown.bs.modal', function () { $('#"
               + component.getMarkupId() + "').focus(); });");
      }
      super.show(target);
      form.clearInput();
   }

   protected abstract Component getFocusComponent();

   public abstract void onSubmit(AjaxRequestTarget target);

   public void onError(AjaxRequestTarget target)
   {

   }

   public void setSubmitButtonVisible(boolean visible)
   {
      submitButton.setVisible(visible);
   }

   public boolean isSubmitButtonVisible()
   {
      return submitButton.isVisible();
   }
}
