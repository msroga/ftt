package pl.myo.service;

import org.springframework.transaction.annotation.Transactional;
import pl.myo.domain.Password;
import pl.myo.domain.User;

/**
 * Created by Marek on 2015-05-22.
 */
public interface IPasswordService extends IAbstractService<Password>
{
   Password getByUser(User user);

   void changePassword(User user, String newPassword);

   Password save(User user, String password);
}
