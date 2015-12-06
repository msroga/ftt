package pl.ftt.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.IStationDao;
import pl.ftt.domain.Station;
import pl.ftt.service.IStationService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
@Service("stationService")
public class StationServiceImpl extends AbstractServiceImpl<Station> implements IStationService
{
   @Resource
   private IStationDao stationDao;

   @Override
   protected IAbstractDao<Station> getDao()
   {
      return stationDao;
   }

   @Override
   @Transactional(readOnly = true)
   public Station getByName(String value)
   {
      return stationDao.getBy(Station.FIELD_NAME, value);
   }

   @Override
   @Transactional(readOnly = true)
   public List<Station> findAllActive()
   {
      return stationDao.findAllActive();
   }
}
