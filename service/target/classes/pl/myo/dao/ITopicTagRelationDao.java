package pl.myo.dao;

import pl.myo.domain.Topic;
import pl.myo.domain.rel.TopicTagRelation;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marek on 2015-05-30.
 */
public interface ITopicTagRelationDao extends IAbstractDao<TopicTagRelation>
{
   List<TopicTagRelation> findBy(Collection<Topic> topics);
}
