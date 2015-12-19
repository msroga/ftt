package pl.ftt.gui.administration.componnents;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.joda.time.LocalTime;
import pl.ftt.core.component.field.ClockPickerField;
import pl.ftt.core.component.form.SearchableListChoice;
import pl.ftt.domain.Station;
import pl.ftt.domain.rel.ConnectionStationRelation;


/**
 * Created by Marek on 2015-11-29.
 */
public abstract class StationRowPanel extends GenericPanel<ConnectionStationRelation>
{
   public StationRowPanel(String id, final IModel<ConnectionStationRelation> model, ListModel<Station> stationListModel)
   {
      super(id, model);

      SearchableListChoice<Station> nameField = new SearchableListChoice<Station>(
              "name",
              new PropertyModel<Station>(model, ConnectionStationRelation.FIELD_STATION),
              stationListModel,
              true);
      nameField.getField().add(new AjaxFormComponentUpdatingBehavior("change")
      {
         @Override
         protected void onUpdate(AjaxRequestTarget target)
         {
            modelChanged();
         }
      });
      nameField.setChoiceRenderer(new StationChoiceRenderer());
      nameField.setLabelVisible(false).setSize(12);
      nameField.setNullValid(true);
      nameField.add(new AttributeModifier("placeholder", new ResourceModel("station.name")));
      add(nameField);

      ClockPickerField arrivalTimeField = new ClockPickerField(
              "arrivalTime", new PropertyModel<LocalTime>(model, ConnectionStationRelation.FIELD_ARRIVAL_TIME));
      arrivalTimeField.add(new AttributeModifier("placeholder", new ResourceModel("arrival.time")));
      arrivalTimeField.setRequired(false);
      arrivalTimeField.add(new AjaxFormComponentUpdatingBehavior("change")
      {
         @Override
         protected void onUpdate(AjaxRequestTarget target)
         {
            modelChanged();
         }
      });
      add(arrivalTimeField);

      ClockPickerField departureTimeField = new ClockPickerField(
              "departureTime", new PropertyModel<LocalTime>(model, ConnectionStationRelation.FIELD_DEPARTURE_TIME));
      departureTimeField.add(new AttributeModifier("placeholder", new ResourceModel("departure.time")));
      departureTimeField.setRequired(true);
      departureTimeField.add(new AjaxFormComponentUpdatingBehavior("change")
      {
         @Override
         protected void onUpdate(AjaxRequestTarget target)
         {
            modelChanged();
         }
      });
      add(departureTimeField);

      AjaxLink<ConnectionStationRelation> moveUpLink = new AjaxLink<ConnectionStationRelation>("moveUpLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            ConnectionStationRelation relation = getModelObject();
            onMoveUp(target, relation);
         }
      };
      add(moveUpLink);

      AjaxLink<ConnectionStationRelation> moveDownLink = new AjaxLink<ConnectionStationRelation>("moveDownLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            ConnectionStationRelation relation = getModelObject();
            onMoveDown(target, relation);
         }
      };
      add(moveDownLink);

      AjaxLink<ConnectionStationRelation> deleteLink = new AjaxLink<ConnectionStationRelation>("deleteLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            ConnectionStationRelation relation = getModelObject();
            onDelete(target, relation);
         }
      };
      add(deleteLink);

   }

   protected abstract void onDelete(AjaxRequestTarget target, ConnectionStationRelation relation);

   protected abstract void onMoveDown(AjaxRequestTarget target, ConnectionStationRelation relation);

   protected abstract void onMoveUp(AjaxRequestTarget target, ConnectionStationRelation relation);
}
