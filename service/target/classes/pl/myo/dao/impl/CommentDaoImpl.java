package pl.myo.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.myo.dao.ICommentDao;
import pl.myo.domain.Comment;
import pl.myo.domain.Topic;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
@Repository("commentDao")
public class CommentDaoImpl extends AbstractDaoImpl<Comment> implements ICommentDao
{
   @Override
   public List<Comment> findByTopic(Topic topic)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(Comment.FIELD_TOPIC, topic));
      criteria.addOrder(Order.asc(Comment.FIELD_CREATED));
      return criteria.list();
   }

   @Override
   public int incrementDoctorLikeCounter(Comment comment)
   {
      Query query = getCurrentSession().getNamedQuery(Comment.QUERY_INCREMENT_DOCTOR_LIKE_COUNTER);
      query.setParameter("id", comment.getId());
      return query.executeUpdate();
   }

   @Override
   public int incrementUserLikeCounter(Comment comment)
   {
      Query query = getCurrentSession().getNamedQuery(Comment.QUERY_INCREMENT_USER_LIKE_COUNTER);
      query.setParameter("id", comment.getId());
      return query.executeUpdate();
   }

   @Override
   public int decrementDoctorLikeCounter(Comment comment)
   {
      Query query = getCurrentSession().getNamedQuery(Comment.QUERY_DECREMENT_DOCTOR_LIKE_COUNTER);
      query.setParameter("id", comment.getId());
      return query.executeUpdate();
   }

   @Override
   public int decrementUserLikeCounter(Comment comment)
   {
      Query query = getCurrentSession().getNamedQuery(Comment.QUERY_DECREMENT_USER_LIKE_COUNTER);
      query.setParameter("id", comment.getId());
      return query.executeUpdate();
   }
}
