package pl.myo.gui.user.components;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.toolbar.FilterToolbar;
import pl.myo.core.renderer.DefaultChoiceRenderer;
import pl.myo.domain.UserTypeEnum;
import pl.myo.domain.filters.UserFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marek on 2015-05-26.
 */
public class UserFilterToolbar extends FilterToolbar<UserFilter>
{
   public UserFilterToolbar(DataTable<?> table)
   {
      super(table);
   }

   @Override
   public void registerFilters(DataTable<?> table, IModel<UserFilter> filterModel)
   {
      TextField<String> loginFilter = new TextField<String>("loginFilter", new PropertyModel<String>(
              filterModel,
              UserFilter.FIELD_LOGIN));
      addFilter(loginFilter, new ResourceModel("user.login"));

      TextField<String> nameFilter = new TextField<String>("nameFilter", new PropertyModel<String>(
              filterModel,
              UserFilter.FIELD_NAME));
      addFilter(nameFilter, new ResourceModel("user.name"));

      TextField<String> emailFilter = new TextField<String>("emailFilter", new PropertyModel<String>(
              filterModel,
              UserFilter.FIELD_EMAIL));
      addFilter(emailFilter, new ResourceModel("user.email"));

      List<UserTypeEnum> choises = Arrays.asList(UserTypeEnum.values());
      DropDownChoice<UserTypeEnum> typeFilter = new DropDownChoice<UserTypeEnum>("typeFilter",
              new PropertyModel<UserTypeEnum>(filterModel, UserFilter.FIELD_TYPE),
              choises, new EnumChoiceRenderer<UserTypeEnum>());
      typeFilter.setEnabled(isTypeEnabled());
      addFilter(typeFilter, new ResourceModel("user.type"));
   }

   protected boolean isTypeEnabled()
   {
      return true;
   }
}
