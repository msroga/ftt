package pl.myo.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.myo.dao.IOpinionDao;
import pl.myo.domain.Opinion;
import pl.myo.domain.OpinionTypeEnum;
import pl.myo.domain.User;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.OpinionFilter;

import java.util.List;

/**
 * Created by Marek on 2015-06-04.
 */
@Repository("opinionDao")
public class OpinionDaoImpl extends AbstractDaoImpl<Opinion> implements IOpinionDao
{
   @Override
   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      Criteria criteria = super.createCriteriaFromSearchParams(filter);
      if (OpinionFilter.class.isAssignableFrom(filter.getClass()))
      {
         OpinionFilter opinionFilter = (OpinionFilter) filter;
         User doctor = opinionFilter.getDoctor();
         OpinionTypeEnum type = opinionFilter.getType();

         if (doctor != null)
         {
            criteria.add(Restrictions.eq(Opinion.FIELD_DOCTOR, doctor));
         }
         if (type != null)
         {
            criteria.add(Restrictions.eq(Opinion.FIELD_TYPE, type));
         }
      }
      return  criteria;
   }

   @Override
   public long count(User author, User doctor)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(Opinion.FIELD_AUTHOR, author));
      criteria.add(Restrictions.eq(Opinion.FIELD_DOCTOR, doctor));
      criteria.setProjection(Projections.rowCount());
      return (Long) criteria.uniqueResult();
   }
}
