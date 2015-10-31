package pl.myo.gui.doctors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
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
import pl.myo.core.pages.AbstractMenuPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.User;
import pl.myo.domain.UserTypeEnum;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.UserFilter;
import pl.myo.gui.doctors.components.DoctorFilterToolbar;
import pl.myo.gui.doctors.components.RatioColumn;
import pl.myo.gui.user.UserDetailsPage;
import pl.myo.gui.user.components.UserFilterToolbar;
import pl.myo.gui.user.components.UserTableHeader;
import pl.myo.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-05-16.
 */
@AuthorizeType(administrator = false, doctor = false, user = true)
@MountPath(MyoApiMappings.DOCTORS_PAGE)
@MenuElement(resourceKey = "menu.administration.doctors", iconName = "fa-user", index = 20)
public class DoctorsPage extends AbstractMenuPage
{
   @SpringBean
   private IUserService userService;

   private DataTable<User> table;

   public DoctorsPage()
   {
      super();
      List<IColumn<User, String>> columns = new ArrayList<>();
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.login"), User.FIELD_LOGIN, User.FIELD_LOGIN));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.name"), User.FIELD_NAME, User.FIELD_NAME));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.surname"), User.FIELD_SURNNAME,
              User.FIELD_SURNNAME));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.email"), User.FIELD_EMAIL, User.FIELD_EMAIL));
      columns.add(new DefaultColumn<User>(new ResourceModel("user.type"), User.FIELD_TYPE, User.FIELD_TYPE));
      columns.add(new RatioColumn(new ResourceModel("doctor.ratio")));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("doctor.positive"), User.FIELD_DOCTOR_POSITIVE_COUNTER));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("doctor.negative"), User.FIELD_DOCTOR_NAGATIVE_COUNTER));

      OpenSearchDescription<User> osd = new OpenSearchDescription<>();
      UserFilter filter = new UserFilter();
      filter.setLogged(loggedUserModel.getObject());
      filter.setType(UserTypeEnum.DOCTOR);
      osd.setFilter(filter);
      EntityProvider<User> provider = new EntityProvider<>(userService, osd);

      table = new DataTable<User>("table", columns, new ResourceModel("users.table.header"), provider,
              true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<User> model)
         {
            setResponsePage(new UserDetailsPage(new PageContext<User>(model.getObject())));
         }
      };
      table.addToolbar(new DoctorFilterToolbar(table));
      bodyContainer.add(table);
   }
}
