package pl.ftt.gui.user.cmp;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.toolbar.FilterToolbar;
import pl.ftt.domain.filters.UserFilter;
import pl.ftt.gui.user.UserDetailsPage;

/**
 * Created by Marek on 2015-11-28.
 */
public class UserFilterToolbar extends FilterToolbar<UserFilter>
{
   public UserFilterToolbar(DataTable<?> table)
   {
      super(table);
      AjaxLink addLink = new AjaxLink("addLink")
      {
         @Override
         public void onClick(AjaxRequestTarget ajaxRequestTarget)
         {
            setResponsePage(new UserDetailsPage());
         }
      };
      form.add(addLink);
   }

   @Override
   public void registerFilters(DataTable<?> table, IModel<UserFilter> filterModel)
   {
      TextField<String> loginFilter = new TextField<String>("loginFilter", new PropertyModel<String>(filterModel, UserFilter.FILTER_LOGIN));
      addFilter(loginFilter, new ResourceModel("login.field"));
   }
}
