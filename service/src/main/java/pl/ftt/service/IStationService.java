package pl.ftt.service;

import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.rel.ConnectionStationRelation;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IStationService extends IAbstractService<Station>
{
   Station getByName(String value);

   List<Station> findAllActive();

   List<ConnectionStationRelation> find(Connection connection);
}
