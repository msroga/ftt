package pl.ftt.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.IPasswordDao;
import pl.ftt.domain.Password;
import pl.ftt.domain.User;
import pl.ftt.service.IPasswordService;

import javax.annotation.Resource;

/**
 * Created by Marek on 2015-11-12.
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
      return passwordDao.getBy(Password.FIELD_USER, user);
   }

   @Override
   @Transactional
   public void save(User user, String password)
   {
      Password pass = new Password();
      pass.setUser(user);
      pass.setPassword(passwordEncoder.encode(password));
      save(pass);
   }

   @Override
   @Transactional
   public void changePassword(User user, String password)
   {
      Password pass = passwordDao.getBy(Password.FIELD_USER, user);
      pass.setPassword(passwordEncoder.encode(password));
      update(pass);
   }
}
