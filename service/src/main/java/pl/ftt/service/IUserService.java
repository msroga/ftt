package pl.ftt.service;

import pl.ftt.domain.User;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IUserService extends IAbstractService<User>
{
   User getByLogin(String login);

   User getByEmail(String email);

   void save(User user, String password);

   void update(User user, String password);
}
