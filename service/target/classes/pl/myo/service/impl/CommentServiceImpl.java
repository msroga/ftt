package pl.myo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.myo.dao.IAbstractDao;
import pl.myo.dao.ICommentDao;
import pl.myo.dao.IUserCommentRelationDao;
import pl.myo.domain.Comment;
import pl.myo.domain.Topic;
import pl.myo.domain.User;
import pl.myo.domain.rel.UserCommentRelation;
import pl.myo.service.ICommentService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-28.
 */
@Service("commentService")
public class CommentServiceImpl extends AbstractServiceImpl<Comment> implements ICommentService
{
   @Resource
   private ICommentDao commentDao;

   @Resource
   private IUserCommentRelationDao commentRelationDao;

   @Override
   protected IAbstractDao<Comment> getDao()
   {
      return commentDao;
   }

   @Override
   @Transactional(readOnly = true)
   public List<Comment> findByTopic(Topic topic)
   {
      return commentDao.findByTopic(topic);
   }

   @Override
   @Transactional
   public void like(Comment comment, User user)
   {
      UserCommentRelation commentRelation = new UserCommentRelation();
      commentRelation.setUser(user);
      commentRelation.setComment(comment);
      commentRelationDao.save(commentRelation);
      if (user.isDoctor())
      {
         commentDao.incrementDoctorLikeCounter(comment);
      }
      else
      {
         commentDao.incrementUserLikeCounter(comment);
      }
   }

   @Override
   @Transactional
   public void dislike(Comment comment, User user)
   {
      commentRelationDao.delete(user, comment);
      if (user.isDoctor())
      {
         commentDao.decrementDoctorLikeCounter(comment);
      }
      else
      {
         commentDao.decrementUserLikeCounter(comment);
      }
   }

   @Override
   @Transactional(readOnly = true)
   public Map<Long, List<User>> findLikes(Topic topic)
   {
      Map<Long, List<User>> map = new HashMap<>();
      List<UserCommentRelation> relations = commentRelationDao.findLikes(topic);
      for (UserCommentRelation relation : relations)
      {
         List<User> list = map.get(relation.getComment());
         if (list == null)
         {
            list = new ArrayList<>();
         }
         list.add(relation.getUser());
         map.put(relation.getComment().getId(), list);
      }
      return map;
   }
}
