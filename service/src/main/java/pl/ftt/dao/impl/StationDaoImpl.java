package pl.ftt.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.ftt.dao.IStationDao;
import pl.ftt.domain.Station;
import pl.ftt.domain.filters.IFilter;
import pl.ftt.domain.filters.StationFilter;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("stationDao")
public class StationDaoImpl extends AbstractDaoImpl<Station> implements IStationDao
{
   @Override
   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      Criteria criteria = super.createCriteriaFromSearchParams(filter);
      if (StationFilter.class.isAssignableFrom(filter.getClass()))
      {
         StationFilter stationFilter = (StationFilter) filter;

         if (StringUtils.isNotBlank(stationFilter.getName()))
         {
            criteria.add(Restrictions.ilike(Station.FIELD_NAME, stationFilter.getName(), MatchMode.START));
         }
      }
      return criteria;
   }

   @Override
   public List<Station> findAllActive()
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(Station.FIELD_ACTIVE, true));
      return criteria.list();
   }
}
