package pl.ftt.gui.newConnetion;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import pl.ftt.core.pages.AbstractDetailsPage;
import pl.ftt.domain.Station;
import pl.ftt.gui.newConnetion.components.AddStationPanel;

/**
 * Created by Marek on 2015-11-24.
 */
public class NewConnectionPage extends AbstractDetailsPage
{
   private Form form;

   public NewConnectionPage()
   {
      form = new Form("form");
      add(form);

      form.add(new AddStationPanel("addStation", new Model<Station>())
      {
         @Override
         protected void onAdd()
         {

         }
      });
   }
}
