package pl.ftt.gui.stations.cmp;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import pl.ftt.domain.Station;
import pl.ftt.service.IStationService;

/**
 * Created by Marek on 2015-12-05.
 */
public class UniqueStationValidator implements IValidator<String>
{
   private final IModel<Station> stationModel;

   @SpringBean
   private IStationService stationService;

   public UniqueStationValidator(IModel<Station> stationModel)
   {
      Injector.get().inject(this);
      this.stationModel = stationModel;
   }

   @Override
   public void validate(IValidatable<String> validatable)
   {
      String value = (String) validatable.getValue();
      Station station = stationService.getByName(value);
      if (station != null)
      {
         if (stationModel != null)
         {
            String name = stationModel.getObject().getName();
            if (!station.getName().equals(name))
            {
               putError(validatable);
            }
         }
         else
         {
            putError(validatable);
         }
      }
   }

   private void putError(IValidatable<String> validatable)
   {
      ValidationError error = new ValidationError(this);
      validatable.error(error);
   }
}
