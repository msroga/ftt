package pl.myo.gui.questions.components;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.MapModel;
import pl.myo.core.component.DefaultLabel;
import pl.myo.domain.Comment;
import pl.myo.domain.Topic;
import pl.myo.domain.User;

import java.util.List;

/**
 * Created by Marek on 2015-05-30.
 */
public abstract class CommentItemPanel extends GenericPanel<Comment>
{
   public CommentItemPanel(String id, IModel<Comment> model, final IModel<User> loggedModel, final MapModel<Long, List<User>> likeMap)
   {
      super(id, model);

      DefaultLabel dateLabel = new DefaultLabel("dateLabel", new PropertyModel<>(model, Comment.FIELD_CREATED));
      add(dateLabel);
      DefaultLabel userLabel = new DefaultLabel("userLabel", new PropertyModel<>(model, Comment.FIELD_USER));
      add(userLabel);

      Label messageLabel = new Label("messageLabel", new PropertyModel<>(model, Comment.FIELD_MESSAGE));
      messageLabel.setEscapeModelStrings(false);
      add(messageLabel);

      Label doctorLikeLabel = new Label("doctorLikeLabel", new PropertyModel<>(model, Comment.FIELD_DOCTOR_LIKE_COUNTER));

      Label userLikeLabel = new Label("userLikeLabel", new PropertyModel<>(model, Comment.FIELD_USER_LIKE_COUNTER));

      AjaxLink<Comment> doctorLikeLink = new AjaxLink<Comment>("doctorLikeLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            User user = loggedModel.getObject();
            Comment comment = getModelObject();
            List<User> users = likeMap.getObject().get(comment.getId());
            boolean isLike = !(CollectionUtils.isNotEmpty(users) && users.contains(user));
            onDoctorLike(target, getModelObject(), isLike);
         }

         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            User user = loggedModel.getObject();
            setEnabled(user.isDoctor());
         }

         @Override
         protected void onComponentTag(ComponentTag tag)
         {
            super.onComponentTag(tag);
            if (isEnabled())
            {
               User user = loggedModel.getObject();
               Comment comment = getModelObject();
               List<User> users = likeMap.getObject().get(comment.getId());
               boolean isLike = !(CollectionUtils.isNotEmpty(users) && users.contains(user));

               if (isLike)
               {
                  tag.put("class", "btn btn-success btn-xs");
               }
               else
               {
                  tag.put("class", "btn btn-danger btn-xs");
               }
            }
         }
      };
      doctorLikeLink.setOutputMarkupId(true);
      doctorLikeLink.add(doctorLikeLabel);
      add(doctorLikeLink);

      AjaxLink<Comment> userLikeLink = new AjaxLink<Comment>("userLikeLink", model)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            User user = loggedModel.getObject();
            Comment comment = getModelObject();
            List<User> users = likeMap.getObject().get(comment.getId());
            boolean isLike = !(CollectionUtils.isNotEmpty(users) && users.contains(user));
            onUserLike(target, getModelObject(), isLike);
         }

         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            User user = loggedModel.getObject();
            setEnabled(!user.isDoctor());
         }

         @Override
         protected void onComponentTag(ComponentTag tag)
         {
            super.onComponentTag(tag);
            if (isEnabled())
            {
               User user = loggedModel.getObject();
               Comment comment = getModelObject();
               List<User> users = likeMap.getObject().get(comment.getId());
               boolean isLike = !(CollectionUtils.isNotEmpty(users) && users.contains(user));

               if (isLike)
               {
                  tag.put("class", "btn btn-success btn-xs");
               }
               else
               {
                  tag.put("class", "btn btn-danger btn-xs");
               }
            }
         }
      };
      userLikeLink.setOutputMarkupId(true);
      userLikeLink.add(userLikeLabel);
      add(userLikeLink);
   }

   protected abstract void onUserLike(AjaxRequestTarget target, Comment comment, boolean isLike);

   protected abstract void onDoctorLike(AjaxRequestTarget target, Comment comment, boolean isLike);
}
