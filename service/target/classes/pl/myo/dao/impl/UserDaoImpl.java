package pl.myo.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.myo.dao.IUserDao;
import pl.myo.domain.Comment;
import pl.myo.domain.Opinion;
import pl.myo.domain.User;
import pl.myo.domain.UserTypeEnum;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.UserFilter;
import pl.myo.domain.rel.PatientDoctorRelation;
import pl.myo.service.IUserService;

import java.util.List;

/**
 * Created by Marek on 2015-05-23.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements IUserDao
{
   @Override
   public User getByLogin(String login)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(User.FIELD_LOGIN, login));
      return (User) criteria.uniqueResult();
   }

   @Override
   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      Criteria criteria = super.createCriteriaFromSearchParams(filter);
      if (UserFilter.class.isAssignableFrom(filter.getClass()))
      {
         UserFilter userFilter = (UserFilter) filter;
         User logged = userFilter.getLogged();
         Assert.notNull(logged);

         String login = userFilter.getLogin();
         String name = userFilter.getName();
         String email = userFilter.getEmail();
         UserTypeEnum type = userFilter.getType();
         Boolean assigned = userFilter.getAssigned();


         if (StringUtils.isNotBlank(login))
         {
            criteria.add(Restrictions.ilike(User.FIELD_LOGIN, login, MatchMode.START));
         }
         if (StringUtils.isNotBlank(name))
         {
            criteria.add(Restrictions.ilike(User.FIELD_NAME, name, MatchMode.START));
         }
         if (StringUtils.isNotBlank(email))
         {
            criteria.add(Restrictions.ilike(User.FIELD_EMAIL, email, MatchMode.START));
         }
         if (type != null)
         {
            criteria.add(Restrictions.eq(User.FIELD_TYPE, type));
         }
         if (assigned != null)
         {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PatientDoctorRelation.class);
            if (logged.isDoctor())
            {
               detachedCriteria.add(Restrictions.eq(PatientDoctorRelation.FIELD_DOCTOR, logged));
               detachedCriteria.setProjection(Projections.property(PatientDoctorRelation.FIELD_PATIENT));
            }
            else
            {
               detachedCriteria.add(Restrictions.eq(PatientDoctorRelation.FIELD_PATIENT, logged));
               detachedCriteria.setProjection(Projections.property(PatientDoctorRelation.FIELD_DOCTOR));
            }
            if (assigned)
            {
               criteria.add(Property.forName(User.FIELD_ID).in(detachedCriteria));
            }
            else
            {
               criteria.add(Restrictions.not(Property.forName(User.FIELD_ID).in(detachedCriteria)));
            }
         }
      }
      return criteria;
   }

   @Override
   public int incrementDoctorPositive(User doctor)
   {
      Query query = getCurrentSession().getNamedQuery(User.QUERY_INCREMENT_DOCTOR_POSITIVE_COUNTER);
      query.setParameter("id", doctor.getId());
      return query.executeUpdate();
   }

   @Override
   public int incrementDoctorNegative(User doctor)
   {
      Query query = getCurrentSession().getNamedQuery(User.QUERY_INCREMENT_DOCTOR_NEGATIVE_COUNTER);
      query.setParameter("id", doctor.getId());
      return query.executeUpdate();
   }

   @Override
   public int decrementDoctorPositive(User doctor)
   {
      Query query = getCurrentSession().getNamedQuery(User.QUERY_DECREMENT_DOCTOR_POSITIVE_COUNTER);
      query.setParameter("id", doctor.getId());
      return query.executeUpdate();
   }

   @Override
   public int decrementDoctorNegative(User doctor)
   {
      Query query = getCurrentSession().getNamedQuery(User.QUERY_DECREMENT_DOCTOR_NEGATIVE_COUNTER);
      query.setParameter("id", doctor.getId());
      return query.executeUpdate();
   }

   @Override
   public List<User> getLastOpinioned()
   {
      Criteria criteria = createCriteria();
      DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Opinion.class);
      detachedCriteria.addOrder(Order.desc(Opinion.FIELD_CREATED));
      detachedCriteria.setProjection(Projections.property(Opinion.FIELD_DOCTOR));
      criteria.add(Property.forName(User.FIELD_ID).in(detachedCriteria));
      criteria.setMaxResults(5);
      criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      return criteria.list();
   }
}
