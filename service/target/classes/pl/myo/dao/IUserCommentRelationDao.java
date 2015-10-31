package pl.myo.dao;

import pl.myo.domain.Comment;
import pl.myo.domain.Topic;
import pl.myo.domain.User;
import pl.myo.domain.rel.UserCommentRelation;

import java.util.List;

/**
 * Created by Marek on 2015-06-04.
 */
public interface IUserCommentRelationDao extends IAbstractDao<UserCommentRelation>
{
   int delete(User user, Comment comment);

   List<UserCommentRelation> findLikes(Topic topic);
}
