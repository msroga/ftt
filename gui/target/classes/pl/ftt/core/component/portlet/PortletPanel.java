package pl.ftt.core.component.portlet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import pl.ftt.domain.AbstractEntity;

public abstract class PortletPanel<T extends AbstractEntity> extends Panel
{
   private static final long serialVersionUID = -5297066177654002761L;

   private boolean editMode = false;

   private AbstractPortletView panel;

   private PortletView<T> readOnlyPanel;

   private PortletForm<T> editablePanel;

   private boolean editable;

   public PortletPanel(String id, IModel<T> model)
   {
      this(id, model, true);
      setOutputMarkupId(true);
      setOutputMarkupId(true);
   }

   public PortletPanel(String id, IModel<T> model, boolean editable)
   {
      super(id, model);
      this.editable = editable;
      setOutputMarkupId(true);
   }

   protected void onModeChange(AjaxRequestTarget target, boolean editMode)
   {

   }

   public void setInMode(AjaxRequestTarget target, boolean edit)
   {
      this.editMode = edit;
      if (readOnlyPanel != null)
      {
         readOnlyPanel.rollback();
      }
      if (editablePanel != null)
      {
         editablePanel.rollback();
      }
      target.add(PortletPanel.this);
      onModeChange(target, editMode);
   }

   private PortletView<T> provideReadOnlyView()
   {
      if (readOnlyPanel == null)
      {
         readOnlyPanel = getReadOnlyView("panel");
      }
      return readOnlyPanel;
   }

   protected PortletView<T> getReadOnlyView(String id)
   {
      return new PortletView<T>(id, (IModel<T>) getDefaultModel())
      {
         @Override
         public void onModeChange(AjaxRequestTarget target)
         {
            setInMode(target, !editMode);
         }

         @Override
         protected AbstractReadOnlyPanel<T> getReadOnlyPanel(String id, IModel<T> model)
         {
            return PortletPanel.this.getReadOnlyPanel(id, model);
         }
      };
   }

   private PortletForm<T> provideEditableView()
   {
      if (editablePanel == null)
      {
         editablePanel = getEditableView("panel");
      }
      return editablePanel;
   }

   protected PortletForm<T> getEditableView(String id)
   {
      return new PortletForm<T>(id, (IModel<T>) getDefaultModel())
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, T object)
         {
            PortletPanel.this.onSubmit(target, object);
            setInMode(target, false);
         }

         @Override
         protected void onError(AjaxRequestTarget target, T object)
         {
            PortletPanel.this.onError(target, object);
         }

         @Override
         protected AbstractEditablePanel<T> getEditablePanel(String id, IModel<T> model)
         {
            return PortletPanel.this.getEditablePanel(id, model);
         }

         @Override
         public void onModeChange(AjaxRequestTarget target)
         {
            if (editMode)
            {
               onCancel(target);
            }
            setInMode(target, !editMode);
         }
      };
   }

   @Override
   protected void onConfigure()
   {
      boolean editable = PortletPanel.this.editable;
      if (!editable && editMode)
      {
         editMode = false;
      }

      if (editMode)
      {
         panel = provideEditableView();
      }
      else
      {
         panel = provideReadOnlyView();
      }
      panel.rollback();
      panel.getEditLink().setVisible(editable);
      addOrReplace(panel);
      super.onConfigure();
   }

   public abstract void onSubmit(AjaxRequestTarget target, T object);

   public abstract AbstractEditablePanel<T> getEditablePanel(String id, IModel<T> model);

   public abstract AbstractReadOnlyPanel<T> getReadOnlyPanel(String id, IModel<T> model);

   protected void onCancel(AjaxRequestTarget target)
   {

   }

   protected void onError(AjaxRequestTarget target, T object)
   {

   }

   public boolean isEditMode()
   {
      return editMode;
   }

   public void setEditMode(boolean editMode)
   {
      this.editMode = editMode;
   }

   public boolean isEditable()
   {
      return editable;
   }

   public void setEditable(boolean editable)
   {
      this.editable = editable;
   }
}
