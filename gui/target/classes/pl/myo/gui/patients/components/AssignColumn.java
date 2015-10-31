package pl.myo.gui.patients.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.MapModel;
import pl.myo.domain.User;

import java.io.Serializable;

/**
 * Created by Marek on 2015-06-13.
 */
public abstract class AssignColumn extends AbstractColumn<User, String>
{
   private final MapModel<User, Boolean> mapModel;

   public AssignColumn(IModel<String> model, MapModel<User, Boolean> mapModel)
   {
      super(model);
      this.mapModel = mapModel;
   }

   @Override
   public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel)
   {
      User user = rowModel.getObject();
      cellItem.add(new ControlPanel(componentId, rowModel, new Model<Boolean>(mapModel.getObject().get(user))));
   }

   public class ControlPanel extends Panel
   {
      private static final long serialVersionUID = 1L;

      public ControlPanel(String id, IModel<User> model, IModel<Boolean> checkBoxModel)
      {
         super(id, model);
         setOutputMarkupId(true);

         AjaxCheckBox checkBox = new AjaxCheckBox("checkBox", checkBoxModel)
         {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
               User object = (User) ControlPanel.this.getDefaultModelObject();
               AssignColumn.this.onSelectionChanged(target, object, getModelObject());
            }
         };
         add(checkBox);
         checkBox.setEnabled(AssignColumn.this.isEnabled());
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

   public abstract void onSelectionChanged(AjaxRequestTarget target, User user, Boolean newSelection);

}