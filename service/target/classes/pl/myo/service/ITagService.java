package pl.myo.service;

import org.springframework.transaction.annotation.Transactional;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-27.
 */
public interface ITagService extends IAbstractService<Tag>
{
   Map<Long,List<Tag>> findAsMap(Collection<Topic> topics);

   List<Tag> findAllActive();
}
