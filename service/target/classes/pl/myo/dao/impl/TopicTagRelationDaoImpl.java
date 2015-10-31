package pl.myo.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.myo.dao.ITopicTagRelationDao;
import pl.myo.domain.AbstractEntity;
import pl.myo.domain.Topic;
import pl.myo.domain.rel.TopicTagRelation;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marek on 2015-05-30.
 */
@Repository("topicTagRelationDao")
public class TopicTagRelationDaoImpl extends AbstractDaoImpl<TopicTagRelation> implements ITopicTagRelationDao
{
   @Override
   public List<TopicTagRelation> findBy(Collection<Topic> topics)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.in(TopicTagRelation.FIELD_TOPIC, topics));
      return criteria.list();
   }
}
