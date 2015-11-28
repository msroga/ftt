package pl.ftt.gui.connections;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.filters.ConnectionFilter;

/**
 * Created by Marek on 2015-11-19.
 */
@MountPath(FttApiMappings.PUBLIC_CONNECTION_PAGE)
@AuthorizeType(guest = true)
@MenuElement(resourceKey = "menu.public.connections", iconName = "fa-search", index = 10)
public class ConnectionsPage extends AbstractMenuPage
{
   private IModel<ConnectionFilter> filterModel = new Model<>();

   public ConnectionsPage()
   {
      super();
      ConnectionFilter filter = new ConnectionFilter();
      filterModel.setObject(filter);

      Form searchForm = new Form("searchForm");
      TextField<String> stationFrom = new TextField<String>("stationFrom",
              new PropertyModel<String>(filterModel, ConnectionFilter.FILTER_STATION_FROM));
      stationFrom.setRequired(true);
      searchForm.add(stationFrom);

      TextField<String> stationTo = new TextField<String>("stationTo",
              new PropertyModel<String>(filterModel, ConnectionFilter.FILTER_STATION_TO));
      stationTo.setRequired(true);
      searchForm.add(stationTo);

      AjaxSubmitLink submitButton = new AjaxSubmitLink("submitButton", searchForm)
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            //todo search
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
}
