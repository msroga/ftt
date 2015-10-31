package pl.myo.gui.user.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.ConfirmationCallListener;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.EntityProvider;
import pl.myo.core.component.table.colums.DefaultColumn;
import pl.myo.core.component.table.colums.IconColumn;
import pl.myo.core.notification.Notification;
import pl.myo.domain.Opinion;
import pl.myo.domain.User;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.OpinionFilter;
import pl.myo.service.IOpinionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-06-10.
 */
public abstract class UserOpinionPanel extends GenericPanel<User>
{
   @SpringBean
   private IOpinionService opinionService;

   private DataTable<Opinion> table;

   public UserOpinionPanel(String id, final IModel<User> model, final IModel<User> loggedUserModel)
   {
      super(id, model);
      setOutputMarkupId(true);

      List<IColumn<Opinion, String>> columns = new ArrayList<>();
      columns.add(new DefaultColumn<Opinion>(new ResourceModel("opinion.message"), Opinion.FIELD_MESSAGE, Opinion.FIELD_MESSAGE));
      columns.add(new DefaultColumn<Opinion>(new ResourceModel("opinion.author"), Opinion.FIELD_AUTHOR, Opinion.FIELD_AUTHOR));
      columns.add(new DefaultColumn<Opinion>(new ResourceModel("opinion.created"), Opinion.FIELD_CREATED, Opinion.FIELD_CREATED));
      columns.add(new DefaultColumn<Opinion>(new ResourceModel("opinion.type"), Opinion.FIELD_TYPE, Opinion.FIELD_TYPE));
      if (loggedUserModel.getObject().isAdministrator())
      {
         columns.add(new IconColumn<Opinion>(null, "fa fa-times", "text-danger")
         {
            @Override
            public void onClick(AjaxRequestTarget target, Opinion object)
            {
               opinionService.delete(object);
               Notification.success(getString("success.opinion.deleted"));
               target.add(table);
            }

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes, Opinion object)
            {
               super.updateAjaxAttributes(attributes, object);
               String message = getString("delete.entity.prompt");
               attributes.getAjaxCallListeners().add(new ConfirmationCallListener(message, object));
            }
         });
      }

      OpenSearchDescription<Opinion> osd = new OpenSearchDescription<>();
      OpinionFilter filter = new OpinionFilter();
      filter.setDoctor(model.getObject());
      osd.setFilter(filter);

      EntityProvider<Opinion> provider = new EntityProvider<Opinion>(opinionService, osd);

      table = new DataTable<Opinion>("table", columns, new ResourceModel("opinions.table.header"), provider)
      {
         @Override
         protected Panel newCaption(String id, IModel<String> caption)
         {
            return new OpinionTableHeader(id, caption, loggedUserModel, model)
            {
               @Override
               protected void onAddClick(AjaxRequestTarget target)
               {
                  UserOpinionPanel.this.onAddClick(target, model.getObject());
               }
            };
         }
      };
      table.setOutputMarkupId(true);
      add(table);
   }

   protected abstract void onAddClick(AjaxRequestTarget target, User doctor);

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      User user = getModelObject();
      setVisible(user.isDoctor());
   }
}
