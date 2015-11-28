package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.IStationDao;
import pl.ftt.domain.Station;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("stationDao")
public class StationDaoImpl extends AbstractDaoImpl<Station> implements IStationDao
{
}
