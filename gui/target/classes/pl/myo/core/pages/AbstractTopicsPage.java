package pl.myo.core.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.table.EntityProvider;
import pl.myo.core.component.views.TopicsView;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.components.TopicFilterToolbar;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.filters.*;
import pl.myo.core.component.window.TopicWindow;
import pl.myo.service.ITagService;
import pl.myo.service.ITopicService;

import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-30.
 */
public abstract class AbstractTopicsPage extends AbstractMenuPage
{
   @SpringBean
   protected ITopicService topicService;

   @SpringBean
   protected ITagService tagService;

   private final MapModel<Long, List<Tag>> tagsMapModel = new MapModel<>();

   protected final WebMarkupContainer topicsContainer;

   protected TopicWindow topicWindow;

   protected TopicFilter topicFilter;

   protected boolean readOnly;

   public AbstractTopicsPage()
   {
      readOnly = isReadOnly();
      OpenSearchDescription<Topic> osd = new OpenSearchDescription<>();
      osd.setFilter(topicFilter = new TopicFilter());
      EntityProvider provider = new EntityProvider<Topic>(topicService, osd)
      {
         @Override
         public List<Topic> find(IFilter filter, SortFilterChain sortFilterChain, int first, int count)
         {
            List<Topic> topics =  super.find(filter, sortFilterChain, first, count);
            Map<Long, List<Tag>> map = tagService.findAsMap(topics);
            tagsMapModel.setObject(map);
            return topics;
         }
      };

      topicsContainer = new WebMarkupContainer("topicsContainer");
      topicsContainer.setOutputMarkupId(true);
      bodyContainer.add(topicsContainer);

      TopicsView topicsView = new TopicsView("topicsView", provider, tagsMapModel, loggedUserModel, readOnly)
      {
         @Override
         protected void onClick(AjaxRequestTarget target, IModel<Topic> model)
         {
            onTopicDetails(target, model);
         }

         @Override
         protected void onEdit(AjaxRequestTarget target, Topic topic)
         {
            topicWindow.setHeader(new ResourceModel("topic.window.edit.header"));
            topicWindow.show(target, topic);
         }

         @Override
         protected void onArchive(AjaxRequestTarget target, Topic topic)
         {
            topic.setActive(false);
            topicService.update(topic);
            Notification.success(getString("success.topic.archived"));
            target.add(topicsContainer);
         }

         @Override
         protected void onDelete(AjaxRequestTarget target, Topic topic)
         {
            topicService.delete(topic);
            Notification.success(getString("success.topic.deleted"));
            target.add(topicsContainer);
         }
      };
      topicsView.setItemsPerPage(10);
      topicsContainer.add(topicsView);

      topicWindow = new TopicWindow("topicWindow", new ResourceModel("topic.window.add.header"))
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Topic topic, List<Tag> tags)
         {
            onAddTopic(target, topic, tags);
            target.add(topicsContainer);
         }
      };
      bodyContainer.add(topicWindow);

      bodyContainer.add(new TopicFilterToolbar("topicFilterToolbar", topicFilter, readOnly)
      {
         @Override
         protected void onAdd(AjaxRequestTarget target)
         {
            topicWindow.setHeader(new ResourceModel("topic.window.add.header"));
            topicWindow.show(target);
         }

         @Override
         protected void onRefresh(AjaxRequestTarget target)
         {
            target.add(topicsContainer);
         }
      });
   }

   protected boolean isReadOnly()
   {
      return false;
   }

   protected abstract void onTopicDetails(AjaxRequestTarget target, IModel<Topic> model);

   protected abstract void onAddTopic(AjaxRequestTarget target, Topic topic, List<Tag> tags);
}
