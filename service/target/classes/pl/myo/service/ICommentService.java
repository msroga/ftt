package pl.myo.service;

import pl.myo.domain.Comment;
import pl.myo.domain.Topic;
import pl.myo.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-28.
 */
public interface ICommentService extends IAbstractService<Comment>
{
   List<Comment> findByTopic(Topic topic);

   void like(Comment comment, User user);

   void dislike(Comment comment, User user);

   Map<Long ,List<User>> findLikes(Topic topic);
}
