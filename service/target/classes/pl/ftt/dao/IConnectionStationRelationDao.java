package pl.ftt.dao;

import pl.ftt.domain.Connection;
import pl.ftt.domain.rel.ConnectionStationRelation;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IConnectionStationRelationDao extends IAbstractDao<ConnectionStationRelation>
{
   List<ConnectionStationRelation> find(Connection connection);
}
