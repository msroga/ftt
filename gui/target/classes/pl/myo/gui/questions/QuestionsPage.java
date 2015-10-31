package pl.myo.gui.questions;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.wicketstuff.annotation.mount.MountPath;
import pl.myo.core.mappings.MyoApiMappings;
import pl.myo.core.menu.MenuElement;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.AbstractMenuPage;
import pl.myo.core.pages.AbstractTopicsPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.gui.articles.ArticleDetailsPage;

import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
@MountPath(MyoApiMappings.QUESTIONS_PAGE)
@MenuElement(resourceKey = "menu.administration.questions", iconName = "fa-question", index = 40)
public class QuestionsPage extends AbstractTopicsPage
{
   public QuestionsPage()
   {
      super();
      topicFilter.setType(TopicTypeEnum.QUESTION);
   }

   @Override
   protected void onTopicDetails(AjaxRequestTarget target, IModel<Topic> model)
   {
      setResponsePage(new QuestionDetailPage((new PageContext<Topic>(model.getObject()))));
   }

   @Override
   protected void onAddTopic(AjaxRequestTarget target, Topic topic, List<Tag> tags)
   {
      if (topic.getId() == null)
      {
         topic.setType(TopicTypeEnum.QUESTION);
         topicService.save(topic, loggedUserModel.getObject(), tags);
         Notification.success(getString("success.question.created"));
      }
      else
      {
         topicService.update(topic, loggedUserModel.getObject(), tags);
         Notification.success(getString("success.question.updated"));
      }
   }

}
