package pl.myo.gui.doctors.components;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.myo.core.component.table.DataTable;
import pl.myo.domain.UserTypeEnum;
import pl.myo.domain.filters.UserFilter;
import pl.myo.gui.user.components.UserFilterToolbar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marek on 2015-06-04.
 */
public class DoctorFilterToolbar extends UserFilterToolbar
{
   public DoctorFilterToolbar(DataTable<?> table)
   {
      super(table);
   }

   @Override
   public void registerFilters(DataTable<?> table, IModel<UserFilter> filterModel)
   {
      super.registerFilters(table, filterModel);

      List<Boolean> choises = Arrays.asList(Boolean.FALSE, Boolean.TRUE);
      DropDownChoice<Boolean> assignedFilter = new DropDownChoice<Boolean>("assignedFilter",
              new PropertyModel<Boolean>(filterModel, UserFilter.FIELD_ASSIGNED),
              choises)
      {
         @Override
         protected String getNullValidDisplayValue()
         {
            return getString("assigned.nullValid");
         }
      };
      assignedFilter.setChoiceRenderer(new ChoiceRenderer<Boolean>()
      {
         @Override
         public Object getDisplayValue(Boolean object)
         {
            String key = "assigned." + object.toString();
            return getString(key);
         }
      });
      assignedFilter.setNullValid(true);
      addFilter(assignedFilter, new ResourceModel("user.assigned"));
   }

   @Override
   protected boolean isTypeEnabled()
   {
      return false;
   }
}
