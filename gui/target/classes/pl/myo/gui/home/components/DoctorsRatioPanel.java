package pl.myo.gui.home.components;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.DefaultLabel;
import pl.myo.domain.User;
import pl.myo.service.IUserService;

import java.util.List;

/**
 * Created by Marek on 2015-06-14.
 */
public class DoctorsRatioPanel extends GenericPanel<User>
{
   @SpringBean
   private IUserService userService;

   public DoctorsRatioPanel(final String id, IModel<User> model)
   {
      super(id, model);
      List<User> doctors = userService.getLastOpinioned();

      ListView<User> listView = new ListView<User>("listView", doctors)
      {
         @Override
         protected void populateItem(ListItem<User> item)
         {
            IModel<User> doctorModel = item.getModel();
            DefaultLabel label = new DefaultLabel("names", doctorModel);
            item.add(label);

            int positive = doctorModel.getObject().getDoctorPositiveCounter();
            int negative = doctorModel.getObject().getDoctorNegativeCounter();
            int sum = positive + negative;
            Float value = null;
            if (sum > 0)
            {
               value = ((float) positive / (float) sum) * 100;
            }

            Label allOpinions = new Label("allOpinions", sum);
            item.add(allOpinions);

            final Float finalValue = value;
            WebMarkupContainer positiveContainer = new WebMarkupContainer("positiveContainer")
            {
               @Override
               protected void onComponentTag(ComponentTag tag)
               {
                  super.onComponentTag(tag);
                  if (finalValue != null)
                  {
                     tag.put("style", "width: " + finalValue + "%");
                  }
               }
            };
            item.add(positiveContainer);

            WebMarkupContainer negativeContainer = new WebMarkupContainer("negativeContainer")
            {
               @Override
               protected void onComponentTag(ComponentTag tag)
               {
                  super.onComponentTag(tag);
                  if (finalValue != null)
                  {
                     tag.put("style", "width: " + (100f - finalValue) + "%");
                  }
               }
            };
            item.add(negativeContainer);
         }
      };
      add(listView);
   }
}
