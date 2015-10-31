package pl.myo.domain.rel;

import pl.myo.domain.AbstractEntity;
import pl.myo.domain.Comment;
import pl.myo.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-06-04.
 */
@Entity
@Table(name = "user_comment_relation")
@NamedQuery(name = UserCommentRelation.QUERY_DELETE_RELATION, query = "delete UserCommentRelation where user = (:user) and comment = (:comment)")
public class UserCommentRelation extends AbstractEntity
{
   public static final String QUERY_DELETE_RELATION = "query.delete.user.comment.relation";

   public static final String FIELD_USER = "user";

   public static final String FIELD_COMMENT = "comment";

   @ManyToOne(optional = false)
   @JoinColumn(name = "user_id")
   @NotNull
   private User user;

   @ManyToOne(optional = false)
   @JoinColumn(name = "comment_id")
   @NotNull
   private Comment comment;

   public User getUser()
   {
      return user;
   }

   public void setUser(User user)
   {
      this.user = user;
   }

   public Comment getComment()
   {
      return comment;
   }

   public void setComment(Comment comment)
   {
      this.comment = comment;
   }
}
