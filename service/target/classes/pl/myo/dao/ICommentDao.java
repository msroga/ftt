package pl.myo.dao;

import pl.myo.domain.Comment;
import pl.myo.domain.Topic;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
public interface ICommentDao extends IAbstractDao<Comment>
{
   List<Comment> findByTopic(Topic topic);

   int incrementDoctorLikeCounter(Comment comment);

   int incrementUserLikeCounter(Comment comment);

   int decrementDoctorLikeCounter(Comment comment);

   int decrementUserLikeCounter(Comment comment);
}
