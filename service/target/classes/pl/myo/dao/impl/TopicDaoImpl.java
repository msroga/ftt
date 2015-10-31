package pl.myo.dao.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.myo.dao.ITopicDao;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.domain.User;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.TopicFilter;
import pl.myo.domain.rel.TopicTagRelation;

import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
@Repository("topicDao")
public class TopicDaoImpl extends AbstractDaoImpl<Topic> implements ITopicDao
{
   @Override
   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      Criteria criteria = super.createCriteriaFromSearchParams(filter);
      if (TopicFilter.class.isAssignableFrom(filter.getClass()))
      {
         TopicFilter topicFilter = (TopicFilter) filter;
         TopicTypeEnum type = topicFilter.getType();
         Assert.notNull(type);

         criteria.add(Restrictions.eq(Topic.FIELD_TYPE, type));
         if (topicFilter.getActive() != null)
         {
            criteria.add(Restrictions.eq(Topic.FIELD_ACTIVE, topicFilter.getActive()));
         }
         if (StringUtils.isNotBlank(topicFilter.getTitle()))
         {
            criteria.add(Restrictions.ilike(Topic.FIELD_TITLE, topicFilter.getTitle(), MatchMode.START));
         }
         if (CollectionUtils.isNotEmpty(topicFilter.getTags()))
         {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TopicTagRelation.class);
            detachedCriteria.add(Restrictions.in(TopicTagRelation.FIELD_TAG, topicFilter.getTags()));
            detachedCriteria.setProjection(Property.forName(TopicTagRelation.FIELD_TOPIC));
            criteria.add(Property.forName(Topic.FIELD_ID).in(detachedCriteria));
         }
      }
      return criteria;
   }

   @Override
   public List<Topic> findBy(User author)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(Topic.FIELD_USER, author));
      criteria.add(Restrictions.eq(Topic.FIELD_ACTIVE, true));
      criteria.addOrder(Order.desc(Topic.FIELD_CREATED));
      return criteria.list();
   }

   @Override
   public Long countBy(User author)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(Topic.FIELD_USER, author));
      criteria.add(Restrictions.eq(Topic.FIELD_ACTIVE, true));
      criteria.setProjection(Projections.rowCount());
      return (Long) criteria.uniqueResult();
   }
}
