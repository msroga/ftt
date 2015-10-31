package pl.ftt.core.component.portlet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.domain.AbstractEntity;

public abstract class PortletForm<T extends AbstractEntity> extends AbstractPortletView<T>
{
   private static final long serialVersionUID = -1283809270557812309L;

   protected final Form form;

   protected final AjaxButton submitButton;

   private final AjaxFallbackLink editLink;

   private final AjaxLink cancelButton;

   public PortletForm(String id, IModel<T> model)
   {
      super(id, model);

      form = new Form("form");

      editLink = createEditLink(new ResourceModel("link.cancel"));
      form.add(editLink);

      submitButton = new AjaxButton("submitButton", form)
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            T object = PortletForm.this.getModelObject();
            PortletForm.this.onSubmit(target, object);
         }

         @Override
         protected void onError(AjaxRequestTarget target, Form<?> form)
         {
            target.add(form);
            T object = PortletForm.this.getModelObject();
            PortletForm.this.onError(target, object);
         }

         @Override
         protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
         {
            super.updateAjaxAttributes(attributes);
//            form.visitChildren(AbstractBootstrapComponent.class, new BootstrapComponentVisitor(attributes));
         }
      };
      form.add(submitButton);
      form.setDefaultButton(submitButton);

      add(form);

      cancelButton = new AjaxLink("cancelButton")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onModeChange(target);
         }
      };
      form.add(cancelButton);

      Panel editablePanel = getEditablePanel("editablePanel", getModel());
      form.add(editablePanel);
   }

   protected abstract void onSubmit(AjaxRequestTarget target, T object);

   protected abstract void onError(AjaxRequestTarget target, T object);

   protected abstract AbstractEditablePanel<T> getEditablePanel(String id, IModel<T> model);

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      cancelButton.setVisible(getModelObject().getId() != null);
      editLink.setVisible(getModelObject().getId() != null);
   }

   @Override
   public AjaxFallbackLink getEditLink()
   {
      return editLink;
   }
}
