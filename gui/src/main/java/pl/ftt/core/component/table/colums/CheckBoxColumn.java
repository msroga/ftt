package pl.ftt.core.component.table.colums;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.io.Serializable;

public abstract class CheckBoxColumn<T extends Serializable> extends AbstractColumn<T, String>
{
   private String propertyField;

   public CheckBoxColumn(IModel<String> model, String propertyField)
   {
      super(model);
      this.propertyField = propertyField;
   }

   @Override
   public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel)
   {
      cellItem.add(new ControlPanel(componentId, rowModel, new PropertyModel<Boolean>(rowModel, propertyField)));
   }

   public class ControlPanel extends Panel
   {
      private static final long serialVersionUID = 1L;

      public ControlPanel(String id, IModel<T> model, IModel<Boolean> checkBoxModel)
      {
         super(id, model);
         setOutputMarkupId(true);

         AjaxCheckBox checkBox = new AjaxCheckBox("checkBox", checkBoxModel)
         {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
               T object = (T) ControlPanel.this.getDefaultModelObject();
               CheckBoxColumn.this.onSelectionChanged(target, object, getModelObject());
            }
         };
         add(checkBox);
         checkBox.setEnabled(CheckBoxColumn.this.isEnabled());
      }
   }

   @Override
   public String getCssClass()
   {
      return "checkbox-column";
   }

   protected boolean isEnabled()
   {
      return true;
   }

   public abstract void onSelectionChanged(AjaxRequestTarget target, T object, Boolean newSelection);

}
