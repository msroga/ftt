package pl.ftt.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.ftt.dao.IUserDao;
import pl.ftt.domain.User;
import pl.ftt.domain.filters.IFilter;
import pl.ftt.domain.filters.UserFilter;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements IUserDao
{
   @Override
   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      Criteria criteria = super.createCriteriaFromSearchParams(filter);
      if (UserFilter.class.isAssignableFrom(filter.getClass()))
      {
         UserFilter userFilter = (UserFilter) filter;

         String login = userFilter.getLogin();
         if (StringUtils.isNotBlank(login))
         {
            criteria.add(Restrictions.ilike(User.FIELD_LOGIN, login, MatchMode.START));
         }
      }
      return criteria;
   }
}
