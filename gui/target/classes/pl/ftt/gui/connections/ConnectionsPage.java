package pl.ftt.gui.connections;

import org.apache.commons.collections4.MapUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalTime;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.component.DefaultLabel;
import pl.ftt.core.component.field.ClockPickerField;
import pl.ftt.core.component.form.BootstrapListMultipleChoice;
import pl.ftt.core.component.form.SearchableListChoice;
import pl.ftt.core.component.portlet.BootstrapViewLabel;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.domain.rel.ConnectionStationRelation;
import pl.ftt.gui.administration.componnents.StationChoiceRenderer;
import pl.ftt.service.IConnectionService;
import pl.ftt.service.IStationService;
import pl.ftt.service.ITagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-11-19.
 */
@MountPath(FttApiMappings.PUBLIC_CONNECTION_PAGE)
@AuthorizeType(guest = true)
@MenuElement(resourceKey = "menu.public.connections", iconName = "fa-search", index = 10)
public class ConnectionsPage extends AbstractMenuPage
{
   private IModel<ConnectionFilter> filterModel = new Model<>();

   private final ListModel<Tag> allTagsModel = new ListModel<>();

   private final ListModel<Station> stationListModel = new ListModel<>();

   private final ListModel<Connection> connectionsModel = new ListModel<>();

   private final MapModel<Connection, List<ConnectionStationRelation>> mapModel = new MapModel<>();

   @SpringBean
   private ITagService tagService;

   @SpringBean
   private IConnectionService connectionService;

   @SpringBean
   private IStationService stationService;

   private WebMarkupContainer resultContainer;

   public ConnectionsPage()
   {
      super();
      ConnectionFilter filter = new ConnectionFilter();
      filterModel.setObject(filter);

      Form searchForm = new Form("searchForm");
      SearchableListChoice<Station> stationFrom = new SearchableListChoice<Station>("stationFrom",
              new PropertyModel<Station>(filterModel, ConnectionFilter.FILTER_STATION_FROM), stationListModel, false);
      stationFrom.setRequired(true);
      stationFrom.setLabelVisible(false).setSize(12);
      stationFrom.setChoiceRenderer(new StationChoiceRenderer());
      searchForm.add(stationFrom);

      SearchableListChoice<Station> stationTo = new SearchableListChoice<Station>("stationTo",
              new PropertyModel<Station>(filterModel, ConnectionFilter.FILTER_STATION_TO), stationListModel, false);
      stationTo.setRequired(true);
      stationTo.setLabelVisible(false).setSize(12);
      stationTo.setChoiceRenderer(new StationChoiceRenderer());
      searchForm.add(stationTo);

      ClockPickerField time = new ClockPickerField(
              "arrivalTime", new PropertyModel<LocalTime>(filterModel, ConnectionFilter.FILTER_TIME));
//      time.add(new AttributeModifier("placeholder", new ResourceModel("arrival.time")));
      searchForm.add(time);

      ListModel<Tag> tagsModel = new ListModel<>();
      tagsModel.setObject(filterModel.getObject().getTags());
      BootstrapListMultipleChoice tagsChoice = new BootstrapListMultipleChoice<Tag>("tags",
              tagsModel,
              new ResourceModel("connection.tags"), allTagsModel, false);
      tagsChoice.setSize(12).setLabelVisible(false);
      searchForm.add(tagsChoice);

      AjaxSubmitLink submitButton = new AjaxSubmitLink("submitButton", searchForm)
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            Map<Connection, List<ConnectionStationRelation>> map = connectionService.find(filterModel.getObject());
            mapModel.setObject(map);
            connectionsModel.setObject(new ArrayList<Connection>(map.keySet()));
            target.add(resultContainer);
         }

         @Override
         protected void onError(AjaxRequestTarget target, Form<?> form)
         {
            super.onError(target, form);
            target.add(form);
         }
      };
      searchForm.setDefaultButton(submitButton);
      searchForm.add(submitButton);
      bodyContainer.add(searchForm);

      resultContainer = new WebMarkupContainer("resultContainer")
      {
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            Map map = mapModel.getObject();
            setVisible(!MapUtils.isEmpty(map));
         }
      };
      resultContainer.setOutputMarkupId(true);
      resultContainer.setOutputMarkupPlaceholderTag(true);
      bodyContainer.add(resultContainer);

      ListView<Connection> connections = new ListView<Connection>("connections", connectionsModel)
      {
         @Override
         protected void populateItem(ListItem<Connection> item)
         {
            item.add(new BootstrapViewLabel("identifier", new ResourceModel("connection.identifier"), this,
                    new PropertyModel<String>(item.getModel(), Connection.FIELD_IDENTIFIER)));
            item.add(new BootstrapViewLabel("comment", new ResourceModel("connection.comment"), this,
                    new PropertyModel<String>(item.getModel(), Connection.FIELD_COMMENT)));

            List<ConnectionStationRelation> relations = mapModel.getObject().get(item.getModelObject());
            item.add(new ListView<ConnectionStationRelation>("station", relations)
            {
               @Override
               protected void populateItem(ListItem<ConnectionStationRelation> item)
               {
                  item.add(new Label("name", new PropertyModel<String>(item.getModel(),
                          ConnectionStationRelation.FIELD_STATION + "." + Station.FIELD_NAME)));
                  item.add(new DefaultLabel("arrivalTime", new PropertyModel<String>(item.getModel(),
                          ConnectionStationRelation.FIELD_ARRIVAL_TIME)));
                  item.add(new DefaultLabel("departureTime", new PropertyModel<String>(item.getModel(),
                          ConnectionStationRelation.FIELD_DEPARTURE_TIME)));
               }
            });
         }
      };
      resultContainer.add(connections);
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      allTagsModel.setObject(tagService.findAll());
      stationListModel.setObject(stationService.findAllActive());
   }
}
