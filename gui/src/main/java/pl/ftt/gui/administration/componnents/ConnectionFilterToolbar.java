package pl.ftt.gui.administration.componnents;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.toolbar.FilterToolbar;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.gui.administration.AdminDetailsPage;

/**
 * Created by Marek on 2015-12-17.
 */
public class ConnectionFilterToolbar extends FilterToolbar<ConnectionFilter>
{
   public ConnectionFilterToolbar(DataTable<?> table)
   {
      super(table);

      AjaxLink addLink = new AjaxLink("addLink")
      {
         @Override
         public void onClick(AjaxRequestTarget ajaxRequestTarget)
         {
            setResponsePage(new AdminDetailsPage());
         }
      };
      form.add(addLink);
   }

   @Override
   public void registerFilters(DataTable<?> table, IModel<ConnectionFilter> filterModel)
   {
//      TextField<String> loginFilter = new TextField<String>("stationFilter", new PropertyModel<String>(filterModel, ConnectionFilter.FILTER_STATION_FROM));
//      addFilter(loginFilter, new ResourceModel("login.field"));
   }
}
