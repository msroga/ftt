package pl.ftt.gui.connections;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalTime;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.component.field.ClockPickerField;
import pl.ftt.core.component.form.BootstrapListMultipleChoice;
import pl.ftt.core.component.form.SearchableListChoice;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.gui.administration.componnents.StationChoiceRenderer;
import pl.ftt.service.IConnectionService;
import pl.ftt.service.IStationService;
import pl.ftt.service.ITagService;

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

   @SpringBean
   private ITagService tagService;

   @SpringBean
   private IConnectionService connectionService;

   @SpringBean
   private IStationService stationService;

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
            connectionService.find(filterModel.getObject());
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

      //todo listwiew
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      allTagsModel.setObject(tagService.findAll());
      stationListModel.setObject(stationService.findAllActive());
   }
}
