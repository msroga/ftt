package pl.ftt.dao.impl;

import org.junit.Test;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.IStationDao;
import pl.ftt.dao.TestDomainObjectFactory;
import pl.ftt.domain.Station;
import pl.ftt.domain.filters.StationFilter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marek on 2016-01-17.
 */
public class StationDaoImplTest extends AbstractDaoImplTest<Station>
{
   @Resource
   private IStationDao stationDao;

   @Override
   protected IAbstractDao<Station> getDao()
   {
      return stationDao;
   }

   @Override
   protected Station getEntity()
   {
      return TestDomainObjectFactory.getStation();
   }

   @Override
   protected List<Station> getEntities()
   {
      return TestDomainObjectFactory.getStations(RANDOM.nextInt(10) + 1);
   }

   @Test
   public void findByName()
   {
      Station station1 = getEntity();
      Station station2 = getEntity();
      station2.setName("test");
      persist(station1, station2);

      StationFilter stationFilter = new StationFilter();
      stationFilter.setName("test");

      List<Station> result = stationDao.findBySearchParams(stationFilter, null, 0 , Integer.MAX_VALUE);
      deepEquals(Arrays.asList(station2), result);
   }
}
