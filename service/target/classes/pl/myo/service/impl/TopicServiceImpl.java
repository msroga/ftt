package pl.myo.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.myo.dao.IAbstractDao;
import pl.myo.dao.ITopicDao;
import pl.myo.dao.ITopicTagRelationDao;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.domain.User;
import pl.myo.domain.rel.TopicTagRelation;
import pl.myo.service.ITopicService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
@Service("topicService")
public class TopicServiceImpl extends AbstractServiceImpl<Topic> implements ITopicService
{
   @Resource
   private ITopicDao topicDao;

   @Resource
   private ITopicTagRelationDao topicTagRelationDao;

   @Override
   protected IAbstractDao<Topic> getDao()
   {
      return topicDao;
   }

   @Override
   @Transactional
   public void save(Topic topic, User createdBy, List<Tag> tags)
   {
      topic.setUser(createdBy);
      topic.setActive(true);
      topic.setCreated(DateTime.now());
      super.save(topic);
      if (CollectionUtils.isNotEmpty(tags))
      {
         for (Tag tag : tags)
         {
            topicTagRelationDao.save(new TopicTagRelation(topic, tag));
         }
      }
   }

   @Override
   @Transactional
   public void update(Topic topic, User editedBy, List<Tag> tags)
   {
      update(topic);
      List<TopicTagRelation> relations = topicTagRelationDao.findBy(Arrays.asList(topic));

      List<TopicTagRelation> toAdd = new ArrayList<>();
      List<TopicTagRelation> toDelete = new ArrayList<>();
      if (CollectionUtils.isNotEmpty(relations))
      {
         if (CollectionUtils.isNotEmpty(tags))
         {
            for (Tag tag: tags)
            {
               if (!contains(relations, tag))
               {
                  toAdd.add(new TopicTagRelation(topic, tag));
               }
            }
         }

         for (TopicTagRelation relation : relations)
         {
            if (!toAdd.contains(relation))
            {
               toDelete.add(relation);
            }
         }
      }
      if (CollectionUtils.isNotEmpty(toDelete))
      {
         topicTagRelationDao.deleteList(toDelete);
      }
      if (CollectionUtils.isNotEmpty(toAdd))
      {
         topicTagRelationDao.saveList(toAdd);
      }
   }

   private boolean contains(List<TopicTagRelation> relations, Tag tag)
   {
      for (TopicTagRelation relation : relations)
      {
         if (relation.getTag().equals(tag))
         {
            return true;
         }
      }
      return false;
   }

   @Override
   @Transactional(readOnly = true)
   public List<Topic> findBy(User author)
   {
      return topicDao.findBy(author);
   }

   @Override
   @Transactional(readOnly = true)
   public Long countBy(User author)
   {
      return topicDao.countBy(author);
   }
}
