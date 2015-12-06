package pl.ftt.gui.newConnetion;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalTime;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.pages.AbstractDetailsPage;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.rel.ConnectionStationRelation;
import pl.ftt.gui.newConnetion.components.StationRowPanel;
import pl.ftt.service.IStationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marek on 2015-11-24.
 */@MountPath(FttApiMappings.PRIVATE_CONNECTION_PAGE)
   @AuthorizeType(administrator = true)
public class NewConnectionPage extends AbstractDetailsPage
{
   private Form form;

   private WebMarkupContainer stationsContainer;

   private final ListModel<ConnectionStationRelation> stationsListModel = new ListModel<>();

   private final IModel<Connection> connectionModel = new Model<>();

   private final ListModel<Station> stationListModel = new ListModel<>();

   @SpringBean
   private IStationService stationService;

   public NewConnectionPage()
   {
      form = new Form("form");
      bodyContainer.add(form);

      AjaxLink addLink = new AjaxLink("addLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            Connection connection = connectionModel.getObject();
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
      bodyContainer.add(addLink);

      stationsContainer = new WebMarkupContainer("stationsContainer");
      stationsContainer.setOutputMarkupId(true);
      form.add(stationsContainer);

     /* RepeatingView stationsView = new RepeatingView("stationsView")
      {
         @Override
         protected void onPopulate()
         {
            super.onPopulate();
            removeAll();
            List<ConnectionStationRelation> relations = stationsListModel.getObject();
            if (CollectionUtils.isNotEmpty(relations))
            {
               for (ConnectionStationRelation relation : relations)
               {
                  StationRowPanel rowPanel = new StationRowPanel(newChildId(), new Model<>(relation), stationListModel)
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
                           target.add(stationsContainer);
                        }
                     }
                  };
                  add(rowPanel);
               }
            }
         }
      };*/

      final ListView<ConnectionStationRelation> stationsView = new ListView<ConnectionStationRelation>("stationsView", stationsListModel)
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
      form.add(stationsContainer);
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      connectionModel.setObject(new Connection());
      stationListModel.setObject(stationService.findAllActive());
   }
}
