package pl.ftt.service.impl;

import org.springframework.stereotype.Service;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.IStationDao;
import pl.ftt.domain.Station;
import pl.ftt.service.IStationService;

import javax.annotation.Resource;

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
}
