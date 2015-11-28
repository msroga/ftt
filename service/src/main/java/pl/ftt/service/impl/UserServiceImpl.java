package pl.ftt.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.IUserDao;
import pl.ftt.domain.User;
import pl.ftt.service.IPasswordService;
import pl.ftt.service.IUserService;

import javax.annotation.Resource;

/**
 * Created by Marek on 2015-11-12.
 */
@Service("userService")
public class UserServiceImpl extends AbstractServiceImpl<User> implements IUserService
{
   @Resource
   private IUserDao userDao;

   @Resource
   private IPasswordService passwordService;

   @Override
   protected IAbstractDao<User> getDao()
   {
      return userDao;
   }

   @Override
   @Transactional(readOnly = true)
   public User getByLogin(String login)
   {
      return userDao.getBy(User.FIELD_LOGIN, login);
   }

   @Override
   @Transactional(readOnly = true)
   public User getByEmail(String email)
   {
      return userDao.getBy(User.FIELD_EMAIL, email);
   }

   @Override
   @Transactional
   public void save(User user, String password)
   {
      save(user);
      passwordService.save(user, password);
   }

   @Override
   @Transactional
   public void update(User user, String password)
   {
      update(user);
      if (!StringUtils.isBlank(password))
      {
         passwordService.changePassword(user, password);
      }
   }
}
