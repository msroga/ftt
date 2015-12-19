package pl.ftt.gui.administration.componnents;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.joda.time.LocalTime;
import pl.ftt.core.component.form.BootstrapListMultipleChoice;
import pl.ftt.core.component.form.TextAreaWithLabel;
import pl.ftt.core.component.form.TextFieldWithLabel;
import pl.ftt.core.component.portlet.AbstractEditablePanel;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;
import pl.ftt.domain.rel.ConnectionStationRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marek on 2015-12-17.
 */
public class ConnectionEditablePanel extends AbstractEditablePanel<Connection>
{
   private WebMarkupContainer stationsContainer;

   private WebMarkupContainer tagsContainer;

   private final ListModel<Station> stationListModel;

   private final ListModel<ConnectionStationRelation> stationsListModel;

   private final ListModel<Tag> tagsModel;

   private final ListModel<Tag> allTagsModel;

   public ConnectionEditablePanel(String id, final IModel<Connection> model,
                                  final ListModel<ConnectionStationRelation> stationsListModel,
                                  final ListModel<Station> stationListModel,
                                  final ListModel<Tag> tagsModel,
                                  final ListModel<Tag> allTagsModel)
   {
      super(id, model);
      this.stationsListModel = stationsListModel;
      this.stationListModel = stationListModel;
      this.tagsModel = tagsModel;
      this.allTagsModel = allTagsModel;

      TextFieldWithLabel<String> identifierField = new TextFieldWithLabel<String>(
              "identifier",
              new PropertyModel<String>(model, Connection.FIELD_IDENTIFIER),
              new ResourceModel("connection.identifier"),
              true);
      add(identifierField);

      TextAreaWithLabel<String> commentField = new TextAreaWithLabel<String>(
              "comment",
              new PropertyModel<String>(model, Connection.FIELD_COMMENT),
              new ResourceModel("connection.comment"),
              true);
      add(commentField);

      AjaxLink addLink = new AjaxLink("addLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            Connection connection = model.getObject();
            Station station = new Station();
            ConnectionStationRelation relation = new ConnectionStationRelation(connection, station);
            List<ConnectionStationRelation> relationList = stationsListModel.getObject();
            if (relationList == null)
            {
               relationList = new ArrayList<ConnectionStationRelation>();
               stationsListModel.setObject(relationList);
            }
            relation.setIndex(relationList.size() + 1);
            relationList.add(relation);
            target.add(stationsContainer);
         }
      };
      add(addLink);

      stationsContainer = new WebMarkupContainer("stationsContainer");
      stationsContainer.setOutputMarkupId(true);
      add(stationsContainer);

      final ListView<ConnectionStationRelation> stationsView = new ListView<ConnectionStationRelation>("stationsView",
              stationsListModel)
      {
         @Override
         protected void populateItem(ListItem<ConnectionStationRelation> components)
         {
            IModel<ConnectionStationRelation> model = components.getModel();
            StationRowPanel rowPanel = new StationRowPanel("panel", model, stationListModel)
            {
               @Override
               protected void onDelete(AjaxRequestTarget target, ConnectionStationRelation relation)
               {
                  List<ConnectionStationRelation> relations = stationsListModel.getObject();
                  if (relations.contains(relation))
                  {
                     relations.remove(relation);
                     target.add(stationsContainer);
                  }
               }

               @Override
               protected void onMoveDown(AjaxRequestTarget target, ConnectionStationRelation relation)
               {
                  List<ConnectionStationRelation> relations = stationsListModel.getObject();
                  int index = relations.indexOf(relation);
                  if (index + 1 < relations.size())
                  {
                     ConnectionStationRelation downRelation = relations.get(index + 1);
                     LocalTime arrivalTime = downRelation.getArrivalTime();
                     LocalTime departureTime = downRelation.getDepartureTime();
                     int idx = downRelation.getIndex();

                     downRelation.setArrivalTime(relation.getArrivalTime());
                     downRelation.setDepartureTime(relation.getDepartureTime());
                     downRelation.setIndex(relation.getIndex());

                     relation.setArrivalTime(arrivalTime);
                     relation.setDepartureTime(departureTime);
                     relation.setIndex(idx);
                     Collections.sort(relations);
                     target.add(stationsContainer);
                  }
               }

               @Override
               protected void onMoveUp(AjaxRequestTarget target, ConnectionStationRelation relation)
               {
                  List<ConnectionStationRelation> relations = stationsListModel.getObject();
                  int index = relations.indexOf(relation);
                  if (index - 1 >= 0)
                  {
                     ConnectionStationRelation upRelation = relations.get(index - 1);
                     LocalTime arrivalTime = upRelation.getArrivalTime();
                     LocalTime departureTime = upRelation.getDepartureTime();
                     int idx = upRelation.getIndex();

                     upRelation.setArrivalTime(relation.getArrivalTime());
                     upRelation.setDepartureTime(relation.getDepartureTime());
                     upRelation.setIndex(relation.getIndex());

                     relation.setArrivalTime(arrivalTime);
                     relation.setDepartureTime(departureTime);
                     relation.setIndex(idx);
                     Collections.sort(relations);
                     target.add(stationsContainer);
                  }
               }
            };
            components.add(rowPanel);
         }
      };
      stationsView.setReuseItems(true);
      stationsContainer.add(stationsView);

      tagsContainer = new WebMarkupContainer("tagsContainer");
      tagsContainer.setOutputMarkupId(true);
      add(tagsContainer);

      BootstrapListMultipleChoice tagsChoice = new BootstrapListMultipleChoice<Tag>("tags", tagsModel,
              new ResourceModel("connection.tags"), allTagsModel, false);
      tagsContainer.add(tagsChoice);
   }
}
