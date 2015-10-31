package pl.myo.gui.home.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.table.AbstractProvider;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.EntityProvider;
import pl.myo.core.component.table.colums.DefaultColumn;
import pl.myo.core.component.table.colums.StyledPropertyColumn;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.domain.User;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.SortFilterChain;
import pl.myo.domain.filters.UserFilter;
import pl.myo.gui.articles.ArticleDetailsPage;
import pl.myo.gui.questions.QuestionDetailPage;
import pl.myo.gui.user.UserDetailsPage;
import pl.myo.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-06-14.
 */
public class OwnPatientsPanel extends GenericPanel<User>
{
   @SpringBean
   private IUserService userService;

   public OwnPatientsPanel(String id, IModel<User> model)
   {
      super(id, model);
      List<IColumn<User, String>> columns = new ArrayList<>();
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.name"), User.FIELD_NAME));
      columns.add(new StyledPropertyColumn<User>(new ResourceModel("user.surname"), User.FIELD_SURNNAME));

      OpenSearchDescription<User> osd = new OpenSearchDescription<User>();
      UserFilter filter = new UserFilter();
      filter.setLogged(model.getObject());
      filter.setAssigned(true);
      osd.setFilter(filter);
      EntityProvider<User> provider = new EntityProvider<>(userService, osd);

      DataTable<User> table = new DataTable<User>("table", columns, new ResourceModel("own.patients.table.header"), provider, 5, true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<User> model)
         {
            super.onRowClick(target, model);
            User user = model.getObject();
            setResponsePage(new UserDetailsPage(new PageContext<User>(user)));
         }
      };
      add(table);
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      User user = getModelObject();
      setVisible(user.isDoctor());
   }
}
