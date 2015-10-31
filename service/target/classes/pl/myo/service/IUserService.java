package pl.myo.service;

import pl.myo.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-22.
 */
public interface IUserService extends IAbstractService<User>
{
   User getByLogin(String login);

   void save(User user, String password);

   void update(User user, String password);

   int incrementDoctorPositive(User doctor);

   int incrementDoctorNegative(User doctor);

   int decrementDoctorPositive(User doctor);

   int decrementDoctorNegative(User doctor);

   Map<User,Boolean> getAsAssigmentMap(List<User> users, User doctor);

   void assing(User patient, User doctor);

   void unassing(User patient, User doctor);

   List<User> getLastOpinioned();

   boolean isAssigned(User patient, User doctor);
}
