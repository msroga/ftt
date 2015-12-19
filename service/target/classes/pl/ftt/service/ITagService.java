package pl.ftt.service;

import pl.ftt.domain.Connection;
import pl.ftt.domain.Tag;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
public interface ITagService extends IAbstractService<Tag>
{
   List<Tag> find(Connection connection);
}
