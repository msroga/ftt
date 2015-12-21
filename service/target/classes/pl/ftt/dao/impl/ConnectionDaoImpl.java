package pl.ftt.dao.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Repository;
import pl.ftt.dao.IConnectionDao;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.domain.filters.IFilter;
import pl.ftt.domain.rel.ConnectionStationRelation;
import pl.ftt.domain.rel.ConnectionTagRelation;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("connectionDao")
public class ConnectionDaoImpl extends AbstractDaoImpl<Connection> implements IConnectionDao
{
   @Override
   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      Criteria criteria = super.createCriteriaFromSearchParams(filter);
      if (ConnectionFilter.class.isAssignableFrom(filter.getClass()))
      {
         ConnectionFilter connectionFilter = (ConnectionFilter) filter;
         Station from = connectionFilter.getStationFrom();
         Station to = connectionFilter.getStationTo();
         LocalTime time = connectionFilter.getTime();
         List<Tag> tags = connectionFilter.getTags();

         if (from != null || to != null || time != null)
         {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConnectionStationRelation.class);
            if (from != null)
            {
//               detachedCriteria.add(Restrictions.eq(ConnectionStationRelation.FIELD_STATION, from));
            }
            if (to != null)
            {
//               detachedCriteria.add(Restrictions.eq(ConnectionStationRelation.FIELD_STATION, to));
            }
            if (time != null)
            {
               detachedCriteria.add(Restrictions.ge(ConnectionStationRelation.FIELD_DEPARTURE_TIME, time));
            }

            criteria.add(Property.forName(Connection.FIELD_ID).in(detachedCriteria));
         }
         if (CollectionUtils.isNotEmpty(tags))
         {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConnectionTagRelation.class);
            detachedCriteria.add(Restrictions.in(ConnectionTagRelation.FIELD_TAG, tags));
            detachedCriteria.setProjection(Projections.distinct(Projections.property(ConnectionTagRelation.FIELD_CONNECTION)));

            criteria.add(Property.forName(Connection.FIELD_ID).in(detachedCriteria));
         }
      }

      return criteria;
   }
}
