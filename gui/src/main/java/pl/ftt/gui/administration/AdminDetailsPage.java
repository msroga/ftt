package pl.ftt.gui.administration;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.component.portlet.AbstractEditablePanel;
import pl.ftt.core.component.portlet.AbstractReadOnlyPanel;
import pl.ftt.core.component.portlet.PortletPanel;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.notification.Notification;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;
import pl.ftt.domain.rel.ConnectionStationRelation;
import pl.ftt.gui.administration.componnents.ConnectionEditablePanel;
import pl.ftt.gui.administration.componnents.ConnectionReadOnlyPanel;
import pl.ftt.service.IConnectionService;
import pl.ftt.service.IStationService;
import pl.ftt.service.ITagService;

/**
 * Created by Marek on 2015-12-17.
 */
@MountPath(FttApiMappings.PRIVATE_CONNECTION_PAGE)
@AuthorizeType(administrator = true)
public class AdminDetailsPage extends AbstractMenuPage
{
   private final IModel<Connection> connectionModel;

   private final ListModel<ConnectionStationRelation> stationsListModel = new ListModel<>();

   private final ListModel<Station> stationListModel = new ListModel<>();

   private final ListModel<Tag> tagsModel = new ListModel<>();

   private final ListModel<Tag> allTagsModel = new ListModel<>();

   @SpringBean
   private IConnectionService connectionService;

   @SpringBean
   private IStationService stationService;

   @SpringBean
   private ITagService tagService;

   public AdminDetailsPage()
   {
      this(new Model<Connection>());
   }

   public AdminDetailsPage(final IModel<Connection> connectionIModel)
   {
      this.connectionModel = connectionIModel;
      PortletPanel<Connection> portletPanel = new PortletPanel<Connection>("portlet", connectionIModel)
      {

         @Override
         public void onSubmit(AjaxRequestTarget target, Connection connection)
         {
            if (connection.getId() == null)
            {
               connectionService.save(connection, stationsListModel.getObject(), tagsModel.getObject());
               Notification.success(getString("success.connection.saved"));
            }
            else
            {
               connectionService.update(connection, stationsListModel.getObject(), tagsModel.getObject());
               Notification.success(getString("success.connection.updated"));
            }
         }

         @Override
         public AbstractEditablePanel<Connection> getEditablePanel(String id, IModel<Connection> model)
         {
            return new ConnectionEditablePanel(id, model, stationsListModel, stationListModel, tagsModel, allTagsModel);
         }

         @Override
         public AbstractReadOnlyPanel<Connection> getReadOnlyPanel(String id, IModel<Connection> model)
         {
            return new ConnectionReadOnlyPanel(id, model, stationsListModel, tagsModel);
         }

         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            if (connectionModel.getObject() == null)
            {
               connectionModel.setObject(new Connection());
            }
         }
      };
      portletPanel.setEditMode(connectionModel.getObject() == null);
      bodyContainer.add(portletPanel);
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      if (connectionModel.getObject() == null)
      {
         connectionModel.setObject(new Connection());
      }
      else
      {
         stationsListModel.setObject(stationService.find(connectionModel.getObject()));
         tagsModel.setObject(tagService.find(connectionModel.getObject()));
      }
      stationListModel.setObject(stationService.findAllActive()); //cache jakis, aby bylo ladnie :)
      allTagsModel.setObject(tagService.findAll());
   }
}
