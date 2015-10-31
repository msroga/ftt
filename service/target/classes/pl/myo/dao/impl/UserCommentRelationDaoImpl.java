package pl.myo.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.myo.dao.IUserCommentRelationDao;
import pl.myo.domain.Comment;
import pl.myo.domain.Topic;
import pl.myo.domain.User;
import pl.myo.domain.rel.UserCommentRelation;

import java.util.List;

/**
 * Created by Marek on 2015-06-04.
 */
@Repository("userCommentRelationDao")
public class UserCommentRelationDaoImpl extends AbstractDaoImpl<UserCommentRelation> implements IUserCommentRelationDao
{
   @Override
   public int delete(User user, Comment comment)
   {
      Query query = getCurrentSession().getNamedQuery(UserCommentRelation.QUERY_DELETE_RELATION);
      query.setParameter(UserCommentRelation.FIELD_USER, user);
      query.setParameter(UserCommentRelation.FIELD_COMMENT, comment);
      return query.executeUpdate();
   }

   @Override
   public List<UserCommentRelation> findLikes(Topic topic)
   {
      Criteria criteria = createCriteria();
      criteria.createAlias(UserCommentRelation.FIELD_COMMENT, UserCommentRelation.FIELD_COMMENT);
      criteria.add(Restrictions.eq(UserCommentRelation.FIELD_COMMENT + "." + Comment.FIELD_TOPIC, topic));
      return criteria.list();
   }
}
