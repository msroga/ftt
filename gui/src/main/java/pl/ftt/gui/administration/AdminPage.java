package pl.ftt.gui.administration;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.EntityProvider;
import pl.ftt.core.component.table.colums.DefaultColumn;
import pl.ftt.core.component.table.colums.StyledPropertyColumn;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.Connection;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.domain.filters.OpenSearchDescription;
import pl.ftt.gui.administration.componnents.ConnectionFilterToolbar;
import pl.ftt.service.IConnectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-11-24.
 */
@MountPath(FttApiMappings.PRIVATE_ADMIN_PAGE)
@AuthorizeType(administrator = true)
@MenuElement(resourceKey = "menu.administration", iconName = "fa-cogs", index = 900)
public class AdminPage extends AbstractMenuPage
{
   @SpringBean
   private IConnectionService connectionService;

   private DataTable<Connection> table;

   public AdminPage()
   {
      List<IColumn<Connection, String>> columns = new ArrayList<>();
      columns.add(new StyledPropertyColumn<Connection>(new ResourceModel("connection.identifier"), Connection.FIELD_IDENTIFIER, Connection.FIELD_IDENTIFIER));
      columns.add(new DefaultColumn<Connection>(new ResourceModel("connection.type"), Connection.FIELD_TYPE, Connection.FIELD_TYPE));
      columns.add(new StyledPropertyColumn<Connection>(new ResourceModel("connection.comment"), Connection.FIELD_COMMENT, Connection.FIELD_COMMENT));

      OpenSearchDescription<Connection> osd = new OpenSearchDescription<>();
      ConnectionFilter connectionFilter = new ConnectionFilter();
      osd.setFilter(connectionFilter);
      EntityProvider provider = new EntityProvider<Connection>(connectionService, osd);


      table = new DataTable<Connection>("table", columns, new ResourceModel("connections.table.header"), provider, 25, true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<Connection> model)
         {
            setResponsePage(new AdminDetailsPage(model));
         }
      };
      table.addToolbar(new ConnectionFilterToolbar(table));
      bodyContainer.add(table);
   }
}
