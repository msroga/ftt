package pl.myo.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import pl.myo.dao.IPasswordDao;
import pl.myo.domain.Password;
import pl.myo.domain.User;

/**
 * Created by Marek on 2015-05-23.
 */
@Repository("passwordDao")
public class PasswordDaoImpl extends AbstractDaoImpl<Password> implements IPasswordDao
{
   @Override
   public Password getByUser(User user)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(Password.FIELD_USER, user));
      return (Password) criteria.uniqueResult();
   }
}
