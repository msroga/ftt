package pl.myo.dao;

import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.domain.User;

import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
public interface ITopicDao extends IAbstractDao<Topic>
{
   List<Topic> findBy(User author);

   Long countBy(User author);
}
