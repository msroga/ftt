package pl.ftt.gui.newConnetion.components;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import pl.ftt.domain.Station;

/**
 * Created by Marek on 2015-12-05.
 */
public class StationChoiceRenderer implements IChoiceRenderer<Station>
{
   @Override
   public Object getDisplayValue(Station station)
   {
      return station.getName();
   }

   @Override
   public String getIdValue(Station station, int i)
   {
      return station.getName();
   }
}
