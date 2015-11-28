package pl.ftt.service;

import pl.ftt.domain.Password;
import pl.ftt.domain.User;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IPasswordService extends IAbstractService<Password>
{
   Password getByUser(User user);

   void save(User user, String password);

   void changePassword(User user, String password);
}
