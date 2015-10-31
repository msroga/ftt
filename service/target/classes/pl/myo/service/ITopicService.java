package pl.myo.service;

import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.domain.User;

import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
public interface ITopicService extends IAbstractService<Topic>
{
   void save(Topic topic, User createdBy, List<Tag> tags);

   void update(Topic topic, User editedBy, List<Tag> tags);

   List<Topic> findBy(User author);

   Long countBy(User author);
}
