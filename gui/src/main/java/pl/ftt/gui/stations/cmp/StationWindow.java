package pl.ftt.gui.stations.cmp;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.core.component.form.TextFieldWithLabel;
import pl.ftt.core.component.window.DefaultWindow;
import pl.ftt.domain.Station;

/**
 * Created by Marek on 2015-12-05.
 */
public abstract class StationWindow extends DefaultWindow
{
   private final IModel<Station> stationModel = new Model<>();

   private TextFieldWithLabel<String> nameField;

   public StationWindow(String id, IModel<String> header)
   {
      super(id, header);

      nameField = new TextFieldWithLabel<String>(
              "name",
              new PropertyModel<String>(stationModel, Station.FIELD_NAME),
              new ResourceModel("station.name"),
              true,
              new UniqueStationValidator(stationModel));
      form.add(nameField);

      TextFieldWithLabel<Double> latField = new TextFieldWithLabel<Double>(
              "lat",
              new PropertyModel<Double>(stationModel, Station.FIELD_GPS_LAT),
              new ResourceModel("station.lat"),
              false);
      form.add(latField);

      TextFieldWithLabel<Double> lonField = new TextFieldWithLabel<Double>(
              "lon",
              new PropertyModel<Double>(stationModel, Station.FIELD_GPS_LON),
              new ResourceModel("station.lon"),
              false);
      form.add(lonField);
   }

   @Override
   protected Component getFocusComponent()
   {
      return nameField.getField();
   }

   @Override
   public void onSubmit(AjaxRequestTarget target)
   {
      Station station = stationModel.getObject();
      onSubmit(target, station);
   }

   protected abstract void onSubmit(AjaxRequestTarget target, Station station);

   @Override
   public void show(AjaxRequestTarget target)
   {
      Station station = new Station();
      station.setActive(true);
      show(target, station);
   }

   public void show(AjaxRequestTarget target, Station station)
   {
      super.show(target);
      stationModel.setObject(station);
   }
}
