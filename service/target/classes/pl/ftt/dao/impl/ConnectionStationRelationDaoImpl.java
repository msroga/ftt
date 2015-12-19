package pl.ftt.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.ftt.dao.IConnectionStationRelationDao;
import pl.ftt.domain.Connection;
import pl.ftt.domain.filters.SortFilterChain;
import pl.ftt.domain.rel.ConnectionStationRelation;

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
}
