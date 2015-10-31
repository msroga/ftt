package pl.myo.dao;

import pl.myo.domain.Password;
import pl.myo.domain.User;

/**
 * Created by Marek on 2015-05-23.
 */
public interface IPasswordDao extends IAbstractDao<Password>
{
   Password getByUser(User user);
}
