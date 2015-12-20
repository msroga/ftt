package pl.ftt.gui.stations;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.component.ConfirmationCallListener;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.EntityProvider;
import pl.ftt.core.component.table.colums.DefaultColumn;
import pl.ftt.core.component.table.colums.IconColumn;
import pl.ftt.core.component.table.colums.StyledPropertyColumn;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.notification.Notification;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.Station;
import pl.ftt.domain.filters.OpenSearchDescription;
import pl.ftt.domain.filters.StationFilter;
import pl.ftt.gui.stations.cmp.StationFilterToolbar;
import pl.ftt.gui.stations.cmp.StationWindow;
import pl.ftt.service.IStationService;

import java.util.ArrayList;
import java.util.List;

@MountPath(FttApiMappings.PRIVATE_STATIONS_PAGE)
@AuthorizeType(administrator = true, user = true)
@MenuElement(resourceKey = "menu.stations", iconName = "fa-home", index = 10)
public class StationsPage extends AbstractMenuPage
{
   @SpringBean
   private IStationService stationService;

   private DataTable<Station> table;

   private StationWindow stationWindow;

   public StationsPage()
   {
      List<IColumn<Station, String>> columns = new ArrayList<>();

      columns.add(new StyledPropertyColumn<Station>(new ResourceModel("station.name"), Station.FIELD_NAME, Station.FIELD_NAME));
      columns.add(new DefaultColumn<Station>(new ResourceModel("station.active"), Station.FIELD_ACTIVE));
      columns.add(new StyledPropertyColumn<Station>(new ResourceModel("station.lat"), Station.FIELD_GPS_LAT));
      columns.add(new StyledPropertyColumn<Station>(new ResourceModel("station.lon"), Station.FIELD_GPS_LON));
      columns.add(new IconColumn<Station>(new ResourceModel("delete.column"), "fa fa-times")
      {
         @Override
         public void onClick(AjaxRequestTarget target, Station station)
         {
            if (station.isActive())
            {
               try
               {
                  stationService.delete(station);
                  Notification.success(getString("entity.deleted"));
               }
               catch (DataIntegrityViolationException | ConstraintViolationException e)
               {
                  station.setActive(false);
                  stationService.update(station);
                  Notification.warn(getString("entity.delete.failed"));
               }
               table.refresh(target);
            }
            else
            {
               Notification.warn(getString("entity.inactive"));
            }
         }

         @Override
         protected void updateAjaxAttributes(AjaxRequestAttributes attributes, Station object)
         {
            super.updateAjaxAttributes(attributes, object);
            String message = getString("entity.delete.prompt");
            attributes.getAjaxCallListeners().add(new ConfirmationCallListener(message, object));
         }
      });

      OpenSearchDescription<Station> osd = new OpenSearchDescription<>();
      StationFilter stationFilter = new StationFilter();
      osd.setFilter(stationFilter);
      EntityProvider provider = new EntityProvider<Station>(stationService, osd);

      table = new DataTable<Station>("table", columns, new ResourceModel("stations.table.header"), provider, 25, true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<Station> model)
         {
            stationWindow.setHeader(new ResourceModel("edit.station.window.header"));
            stationWindow.show(target, model.getObject());
         }
      };
      table.addToolbar(new StationFilterToolbar(table)
      {
         @Override
         protected void onAdd(AjaxRequestTarget target)
         {
            stationWindow.setHeader(new ResourceModel("add.station.window.header"));
            stationWindow.show(target);
         }
      });
      bodyContainer.add(table);

      stationWindow = new StationWindow("stationWindow", new ResourceModel("add.station.window.header"))
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Station station)
         {
            if (station.getId() == null)
            {
               stationService.save(station);
               Notification.success(getString("entity.saved"));
            }
            else
            {
               stationService.update(station);
               Notification.success(getString("entity.updated"));
            }
            table.refresh(target);
         }
      };
      bodyContainer.add(stationWindow);
   }
}
