package pl.myo.gui.patients;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import pl.myo.core.authentication.AuthorizeType;
import pl.myo.core.component.ConfirmationCallListener;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.EntityProvider;
import pl.myo.core.component.table.colums.DefaultColumn;
import pl.myo.core.component.table.colums.IconColumn;
import pl.myo.core.component.table.colums.StyledPropertyColumn;
import pl.myo.core.mappings.MyoApiMappings;
import pl.myo.core.menu.MenuElement;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.AbstractMenuPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.User;
import pl.myo.domain.UserTypeEnum;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.SortFilterChain;
import pl.myo.domain.filters.UserFilter;
import pl.myo.gui.patients.components.AssignColumn;
import pl.myo.gui.patients.components.PatientFilterToolbar;
import pl.myo.gui.user.UserDetailsPage;
import pl.myo.gui.user.components.UserFilterToolbar;
import pl.myo.gui.user.components.UserTableHeader;
import pl.myo.service.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-16.
 */
@AuthorizeType(administrator = false, doctor = true, user = false)
@MountPath(MyoApiMappings.PATIENTS_PAGE)
@MenuElement(resourceKey = "menu.administration.patients", iconName = "fa-user", index = 20)
public class PatientsPage extends AbstractMenuPage
{
   @SpringBean
   private IUserService userService;

   private DataTable<User> table;

   private final MapModel<User, Boolean> mapModel = new MapModel<>();

   public PatientsPage()
   {
      super();
      List<IColumn<User, String>> columns = new ArrayList<>();
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.login"), User.FIELD_LOGIN, User.FIELD_LOGIN));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.name"), User.FIELD_NAME, User.FIELD_NAME));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.surname"), User.FIELD_SURNNAME,
              User.FIELD_SURNNAME));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.email"), User.FIELD_EMAIL, User.FIELD_EMAIL));
      columns.add(new DefaultColumn<User>(new ResourceModel("user.type"), User.FIELD_TYPE, User.FIELD_TYPE));
      columns.add(new AssignColumn(new ResourceModel("user.assigned"), mapModel)
      {
         @Override
         public void onSelectionChanged(AjaxRequestTarget target, User user, Boolean newSelection)
         {
            if (newSelection.equals(Boolean.TRUE))
            {
               //przypisz
               userService.assing(user, loggedUserModel.getObject());
               Notification.success(getString("success.patient.assigned"));
            }
            else
            {
               //odpisz
               userService.unassing(user, loggedUserModel.getObject());
               Notification.success(getString("success.patient.unassigned"));
            }
         }
      });

      OpenSearchDescription<User> osd = new OpenSearchDescription<>();
      UserFilter filter = new UserFilter();
      filter.setType(UserTypeEnum.USER);
      filter.setLogged(loggedUserModel.getObject());
      osd.setFilter(filter);
      EntityProvider<User> provider = new EntityProvider<User>(userService, osd)
      {
         @Override
         public List<User> find(IFilter filter, SortFilterChain sortFilterChain, int first, int count)
         {
            List<User> users = super.find(filter, sortFilterChain, first, count);
            if (CollectionUtils.isNotEmpty(users))
            {
               Map<User, Boolean> map = userService.getAsAssigmentMap(users, loggedUserModel.getObject());
               mapModel.setObject(map);
            }
            else
            {
               mapModel.setObject(new HashMap<User, Boolean>());
            }
            return users;
         }
      };

      table = new DataTable<User>("table", columns, new ResourceModel("users.table.header"), provider,
              true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<User> model)
         {
            setResponsePage(new UserDetailsPage(new PageContext<User>(model.getObject())));
         }
      };
      table.addToolbar(new PatientFilterToolbar(table));
      bodyContainer.add(table);
   }
}
