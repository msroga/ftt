package pl.ftt.dao;

import pl.ftt.domain.Connection;
import pl.ftt.domain.Tag;
import pl.ftt.domain.rel.ConnectionTagRelation;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IConnectionTagRelationDao extends IAbstractDao<ConnectionTagRelation>
{
   List<Tag> find(Connection connection);

   List<ConnectionTagRelation> findBy(Connection connection);
}
