package pl.ftt.service;

import pl.ftt.domain.Connection;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.domain.rel.ConnectionStationRelation;

import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IConnectionService extends IAbstractService<Connection>
{
   void save(Connection connection, List<ConnectionStationRelation> stations, List<Tag> tags);

   void update(Connection connection, List<ConnectionStationRelation> stations, List<Tag> tags);

   Map<Connection, List<ConnectionStationRelation>> find(ConnectionFilter filter);
}
