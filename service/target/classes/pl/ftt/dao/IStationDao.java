package pl.ftt.dao;

import pl.ftt.domain.Station;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IStationDao extends IAbstractDao<Station>
{
   List<Station> findAllActive();
}
