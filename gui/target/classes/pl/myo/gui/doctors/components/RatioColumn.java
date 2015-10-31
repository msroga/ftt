package pl.myo.gui.doctors.components;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import pl.myo.domain.User;

/**
 * Created by Marek on 2015-06-13.
 */
public class RatioColumn extends PropertyColumn<User, String>
{
   public RatioColumn(IModel<String> displayModel)
   {
      super(displayModel, null);
   }

   @Override
   public void populateItem(Item<ICellPopulator<User>> item, String componentId, IModel<User> rowModel)
   {
      User user = rowModel.getObject();
      item.add(new Label(componentId, generateLabel(user)));
   }

   private String generateLabel(User user)
   {
      if (user.isDoctor())
      {
         int positive = user.getDoctorPositiveCounter();
         int negative = user.getDoctorNegativeCounter();
         int sum = positive + negative;
         if (sum > 0)
         {
            return String.valueOf(((float) positive / (float) sum) * 100) + "%";
         }
      }
      return null;
   }
}
