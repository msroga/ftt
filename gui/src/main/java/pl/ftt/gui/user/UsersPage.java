package pl.ftt.gui.user;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.component.ConfirmationCallListener;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.EntityProvider;
import pl.ftt.core.component.table.colums.IconColumn;
import pl.ftt.core.component.table.colums.StyledPropertyColumn;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.User;
import pl.ftt.domain.filters.OpenSearchDescription;
import pl.ftt.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-11-28.
 */
@MountPath(FttApiMappings.PRIVATE_USERS_PAGE)
@AuthorizeType(administrator = true)
@MenuElement(resourceKey = "menu.users", iconName = "fa-user", index = 10)
public class UsersPage extends AbstractMenuPage
{
   @SpringBean
   private IUserService userService;

   private DataTable<User> table;

   public UsersPage()
   {
      List<IColumn<User, String>> columns = new ArrayList<>();

      columns.add(new StyledPropertyColumn<User>(new ResourceModel("login.field"), User.FIELD_LOGIN, User.FIELD_LOGIN));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("email.field"), User.FIELD_EMAIL, User.FIELD_EMAIL));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("first.name.field"), User.FIELD_FIRST_NAME, User.FIELD_FIRST_NAME));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("last.name.field"), User.FIELD_LAST_NAME, User.FIELD_LAST_NAME));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("type.field"), User.FIELD_TYPE, User.FIELD_TYPE));
      columns.add(new IconColumn<User>(new ResourceModel("delete.column"), "fa fa-times")
      {
         @Override
         public void onClick(AjaxRequestTarget target, User user)
         {
            userService.delete(user);
            table.refresh(target);
         }

         @Override
         protected void updateAjaxAttributes(AjaxRequestAttributes attributes, User object)
         {
            super.updateAjaxAttributes(attributes, object);
            String message = getString("entity.delete.prompt");
            attributes.getAjaxCallListeners().add(new ConfirmationCallListener(message, object));
         }
      });

      OpenSearchDescription<User> osd = new OpenSearchDescription<>();
      EntityProvider provider = new EntityProvider<User>(userService, osd);

      table = new DataTable<User>("table", columns, new ResourceModel("users.table.header"), provider, 25, true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<User> model)
         {
            setResponsePage(new UserDetailsPage(model));
         }
      };
      bodyContainer.add(table);
   }
}
