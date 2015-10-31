package pl.myo.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.myo.dao.IAbstractDao;
import pl.myo.dao.ITagDao;
import pl.myo.dao.ITopicTagRelationDao;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.rel.TopicTagRelation;
import pl.myo.service.ITagService;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Marek on 2015-05-27.
 */
@Service("tagService")
public class TagServiceImpl extends AbstractServiceImpl<Tag> implements ITagService
{
   @Resource
   private ITagDao tagDao;

   @Resource
   private ITopicTagRelationDao topicTagRelationDao;

   @Override
   protected IAbstractDao<Tag> getDao()
   {
      return tagDao;
   }

   @Override
   @Transactional(readOnly = true)
   public Map<Long, List<Tag>> findAsMap(Collection<Topic> topics)
   {
      Map<Long, List<Tag>> map = new HashMap<>();
      List<TopicTagRelation> relations = topicTagRelationDao.findBy(topics);
      if (CollectionUtils.isNotEmpty(relations))
      {
         for (TopicTagRelation relation : relations)
         {
            List<Tag> tags = map.get(relation.getTopic().getId());
            if (tags == null)
            {
               tags = new ArrayList<>();
            }
            tags.add(relation.getTag());
            map.put(relation.getTopic().getId(), tags);
         }
      }

      return map;
   }

   @Override
   @Transactional
   public void delete(Tag entity)
   {
      try
      {
         super.delete(entity);
      }
      catch (DataIntegrityViolationException | ConstraintViolationException e)
      {
         entity.setActive(false);
         update(entity);
      }
   }

   @Override
   @Transactional(readOnly = true)
   public List<Tag> findAllActive()
   {
      return tagDao.findBy(Tag.FIELD_ACTIVE, true);
   }
}
