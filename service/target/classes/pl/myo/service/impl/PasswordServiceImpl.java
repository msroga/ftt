package pl.myo.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.myo.dao.IAbstractDao;
import pl.myo.dao.IPasswordDao;
import pl.myo.domain.Password;
import pl.myo.domain.User;
import pl.myo.service.IPasswordService;

import javax.annotation.Resource;

/**
 * Created by Marek on 2015-05-22.
 */
@Service("passwordService")
public class PasswordServiceImpl extends AbstractServiceImpl<Password> implements IPasswordService
{
   @Resource
   private IPasswordDao passwordDao;

   @Resource
   protected PasswordEncoder passwordEncoder;

   @Override
   protected IAbstractDao<Password> getDao()
   {
      return passwordDao;
   }

   @Override
   @Transactional(readOnly = true)
   public Password getByUser(User user)
   {
      return passwordDao.getByUser(user);
   }

   @Override
   @Transactional
   public void changePassword(User user, String newPassword)
   {
      Password password = getByUser(user);
      password.setPassword(passwordEncoder.encode(newPassword));
      getDao().update(password);
   }

   @Override
   @Transactional
   public Password save(User user, String newPassword)
   {
      Password password = new Password();
      password.setUser(user);
      password.setPassword(passwordEncoder.encode(newPassword));
      return passwordDao.save(password);
   }
}
