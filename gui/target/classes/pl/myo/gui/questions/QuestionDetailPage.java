package pl.myo.gui.questions;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import pl.myo.core.component.DefaultLabel;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.AbstractDetailsPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Comment;
import pl.myo.domain.Topic;
import pl.myo.domain.User;
import pl.myo.gui.questions.components.CommentItemPanel;
import pl.myo.gui.questions.components.NewCommentPanel;
import pl.myo.service.ICommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-30.
 */
public class QuestionDetailPage extends AbstractDetailsPage<Topic>
{
   private final ListModel<Comment> commentsModel = new ListModel<>();

   private final WebMarkupContainer commentsContainer;

   private final IModel<Topic> model = new Model<>();

   private final MapModel<Long, List<User>> likeMap = new MapModel<>();

   @SpringBean
   private ICommentService commentService;

   public QuestionDetailPage(PageContext<Topic> pageContext)
   {
      model.setObject(pageContext.getContext());
      Label titleLabel = new Label("titleLabel", new PropertyModel<>(model, Topic.FIELD_TITLE));
      bodyContainer.add(titleLabel);
      DefaultLabel dateLabel = new DefaultLabel("dateLabel", new PropertyModel<>(model, Topic.FIELD_CREATED));
      bodyContainer.add(dateLabel);
      DefaultLabel userLabel = new DefaultLabel("userLabel", new PropertyModel<>(model, Topic.FIELD_USER));
      bodyContainer.add(userLabel);

      Label messageLabel = new Label("messageLabel", new PropertyModel<>(model, Topic.FIELD_MESSAGE));
      messageLabel.setEscapeModelStrings(false);
      bodyContainer.add(messageLabel);

      commentsContainer = new WebMarkupContainer("commentsContainer")
      {
         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            List<Comment> comments = commentService.findByTopic(model.getObject());
            commentsModel.setObject(comments);
         }
      };
      commentsContainer.setOutputMarkupId(true);
      bodyContainer.add(commentsContainer);

      ListView<Comment> commentsView = new ListView<Comment>("commentsView", commentsModel)
      {
         @Override
         protected void populateItem(ListItem<Comment> item)
         {
            item.add(new CommentItemPanel("commentPanel", item.getModel(), loggedUserModel, likeMap)
            {
               @Override
               protected void onUserLike(AjaxRequestTarget target, Comment comment, boolean isLike)
               {
                  User user = loggedUserModel.getObject();
                  if (isLike)
                  {
                     commentService.like(comment, user);
                     Notification.success(getString("success.like"));
                  }
                  else
                  {
                     commentService.dislike(comment, user);
                     Notification.success(getString("success.dislike"));
                  }
                  refreshModels();
                  target.add(commentsContainer);
               }

               @Override
               protected void onDoctorLike(AjaxRequestTarget target, Comment comment, boolean isLike)
               {
                  User user = loggedUserModel.getObject();
                  if (isLike)
                  {
                     commentService.like(comment, user);
                     Notification.success(getString("success.like"));
                  }
                  else
                  {
                     commentService.dislike(comment, user);
                     Notification.success(getString("success.dislike"));
                  }
                  refreshModels();
                  target.add(commentsContainer);
               }
            });
         }
      };
      commentsContainer.add(commentsView);

      NewCommentPanel newCommentPanel = new NewCommentPanel("newCommentPanel")
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Comment comment)
         {
            comment.setCreated(DateTime.now());
            comment.setTopic(model.getObject());
            comment.setUser(loggedUserModel.getObject());
            commentService.save(comment);
            Notification.success(getString("success.comment.created"));
            target.add(commentsContainer);
         }

         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            Topic topic = model.getObject();
            setVisible(topic.isActive());
         }
      };
      bodyContainer.add(newCommentPanel);
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      refreshModels();
   }

   private void refreshModels()
   {
      Topic topic = model.getObject();
      Map<Long, List<User>> map = commentService.findLikes(topic);
      if (map == null)
      {
         map = new HashMap<Long, List<User>>();
      }
      likeMap.setObject(map);
   }
}
