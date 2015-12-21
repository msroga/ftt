package pl.ftt.dao.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Repository;
import pl.ftt.dao.IConnectionStationRelationDao;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.domain.filters.SortFilterChain;
import pl.ftt.domain.rel.ConnectionStationRelation;
import pl.ftt.domain.rel.ConnectionTagRelation;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("connectionStationRelationDao")
public class ConnectionStationRelationDaoImpl extends AbstractDaoImpl<ConnectionStationRelation> implements
        IConnectionStationRelationDao
{
   @Override
   public List<ConnectionStationRelation> find(Connection connection)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(ConnectionStationRelation.FIELD_CONNECTION, connection));
      addOrder(criteria, new SortFilterChain(ConnectionStationRelation.FIELD_INDEX, true));
      return criteria.list();
   }

   @Override
   public List<ConnectionStationRelation> find(ConnectionFilter filter)
   {
      Criteria criteria = createCriteria();

      Station from = filter.getStationFrom();
      Station to = filter.getStationTo();
      LocalTime time = filter.getTime();
      List<Tag> tags = filter.getTags();

      if (from != null || to != null)
      {
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConnectionStationRelation.class);
         Disjunction disjunction = Restrictions.disjunction();
         if (from != null)
         {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq(ConnectionStationRelation.FIELD_STATION, from));
            if (time != null)
            {
               conjunction.add(Restrictions.ge(ConnectionStationRelation.FIELD_DEPARTURE_TIME, time));
            }
            disjunction.add(conjunction);
         }
         if (to != null)
         {
            disjunction.add(Restrictions.eq(ConnectionStationRelation.FIELD_STATION, to));
         }
         detachedCriteria.add(disjunction);
         detachedCriteria.setProjection(Projections.distinct(Projections.property(ConnectionStationRelation.FIELD_CONNECTION)));
         criteria.add(Property.forName(ConnectionStationRelation.FIELD_CONNECTION).in(detachedCriteria));
      }
      if (CollectionUtils.isNotEmpty(tags))
      {
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConnectionTagRelation.class);
         detachedCriteria.add(Restrictions.in(ConnectionTagRelation.FIELD_TAG, tags));
         detachedCriteria.setProjection(
                 Projections.distinct(Projections.property(ConnectionTagRelation.FIELD_CONNECTION)));

         criteria.add(Property.forName(ConnectionStationRelation.FIELD_CONNECTION).in(detachedCriteria));
      }

      return criteria.list();
   }
}
