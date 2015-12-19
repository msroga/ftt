package pl.ftt.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.ftt.dao.IConnectionTagRelationDao;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Tag;
import pl.ftt.domain.rel.ConnectionTagRelation;

import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("connectionTagRelationDao")
public class ConnectionTagRelationDaoImpl extends AbstractDaoImpl<ConnectionTagRelation> implements
        IConnectionTagRelationDao
{
   @Override
   public List<Tag> find(Connection connection)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(ConnectionTagRelation.FIELD_CONNECTION, connection));
      criteria.setProjection(Projections.property(ConnectionTagRelation.FIELD_TAG));
      return criteria.list();
   }

   @Override
   public List<ConnectionTagRelation> findBy(Connection connection)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(ConnectionTagRelation.FIELD_CONNECTION, connection));
      return criteria.list();
   }
}
