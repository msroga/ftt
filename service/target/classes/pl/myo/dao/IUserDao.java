package pl.myo.dao;

import pl.myo.domain.User;

import java.util.List;

/**
 * Created by Marek on 2015-05-23.
 */
public interface IUserDao extends IAbstractDao<User>
{
   User getByLogin(String login);

   int incrementDoctorPositive(User doctor);

   int incrementDoctorNegative(User doctor);

   int decrementDoctorPositive(User doctor);

   int decrementDoctorNegative(User doctor);

   List<User> getLastOpinioned();
}
