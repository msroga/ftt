package pl.myo.gui.articles;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.wicketstuff.annotation.mount.MountPath;
import pl.myo.core.mappings.MyoApiMappings;
import pl.myo.core.menu.MenuElement;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.AbstractTopicsPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;

import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
@MountPath(MyoApiMappings.ARTICLES_PAGE)
@MenuElement(resourceKey = "menu.administration.articles", iconName = "fa-book ", index = 30)
public class ArticlesPage extends AbstractTopicsPage
{
   public ArticlesPage()
   {
      super();
      topicFilter.setType(TopicTypeEnum.ARTICLE);
   }

   @Override
   protected void onTopicDetails(AjaxRequestTarget target, IModel<Topic> model)
   {
      setResponsePage(new ArticleDetailsPage(new PageContext<Topic>(model.getObject())));
   }

   @Override
   protected void onAddTopic(AjaxRequestTarget target, Topic topic, List<Tag> tags)
   {
      if (topic.getId() == null)
      {
         topic.setType(TopicTypeEnum.ARTICLE);
         topicService.save(topic, loggedUserModel.getObject(), tags);
         Notification.success(getString("success.created.article"));
      }
      else
      {
         topicService.update(topic, loggedUserModel.getObject(), tags);
         Notification.success(getString("success.updated.article"));
      }
   }

   @Override
   protected boolean isReadOnly()
   {
      return loggedUserModel.getObject().isUser();
   }
}
