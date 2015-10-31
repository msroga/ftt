package pl.myo.core.component.views;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.DefaultLabel;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.User;
import pl.myo.service.ISecurityService;

import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
public abstract class TopicItemPanel extends GenericPanel<Topic>
{
   public TopicItemPanel(String id, final IModel<Topic> model, MapModel<Long, List<Tag>> tagsMapModel , final IModel<User> loggedModel, boolean readOnly)
   {
      super(id, model);
      Label titleLabel = new Label("titleLabel", new PropertyModel<>(model, Topic.FIELD_TITLE));
      add(titleLabel);
      DefaultLabel dateLabel = new DefaultLabel("dateLabel", new PropertyModel<>(model, Topic.FIELD_CREATED));
      add(dateLabel);
      DefaultLabel userLabel = new DefaultLabel("userLabel", new PropertyModel<>(model, Topic.FIELD_USER));
      add(userLabel);

      add(new AjaxEventBehavior("dblclick")
      {
         @Override
         protected void onEvent(AjaxRequestTarget target)
         {
            onClick(target, model);
         }
      });

      WebMarkupContainer tagsContainer = new WebMarkupContainer("tagsContainer");
      tagsContainer.setOutputMarkupId(true);
      tagsContainer.setOutputMarkupPlaceholderTag(true);
      add(tagsContainer);

      List<Tag> tags = tagsMapModel.getObject().get(model.getObject().getId());
      ListView<Tag> listView = new ListView<Tag>("tags", tags)
      {
         @Override
         protected void populateItem(ListItem<Tag> item)
         {
            item.add(new DefaultLabel("tag", item.getModel()));
         }
      };
      tagsContainer.add(listView);

      WebMarkupContainer actionsContainer = new WebMarkupContainer("actionsContainer");
      actionsContainer.setOutputMarkupId(true);
      actionsContainer.setOutputMarkupPlaceholderTag(true);
      actionsContainer.setVisible(!readOnly);
      add(actionsContainer);

      AjaxLink<Topic> deleteLink = new AjaxLink<Topic>("deleteLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onDelete(target, getModelObject());
         }
      };
      deleteLink.setVisible(loggedModel.getObject().isAdministrator());
      actionsContainer.add(deleteLink);

      AjaxLink<Topic> archiveLink = new AjaxLink<Topic>("archiveLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onArchive(target, getModelObject());
         }
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            Topic topic = getModelObject();
            User logged = loggedModel.getObject();
            setVisible((logged.isAdministrator() || logged.isDoctor()) && topic.isActive());
         }
      };
      actionsContainer.add(archiveLink);

      AjaxLink<Topic> editLink = new AjaxLink<Topic>("editLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onEdit(target, getModelObject());
         }

         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            Topic topic = getModelObject();
            User logged = loggedModel.getObject();
            setVisible((logged.isAdministrator() || topic.getUser().equals(logged)) && topic.isActive());
         }
      };
      actionsContainer.add(editLink);
   }

   protected abstract void onEdit(AjaxRequestTarget target, Topic topic);

   protected abstract void onArchive(AjaxRequestTarget target, Topic topic);

   protected abstract void onDelete(AjaxRequestTarget target, Topic topic);

   protected abstract void onClick(AjaxRequestTarget target, IModel<Topic> model);
}
