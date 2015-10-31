package pl.myo.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.myo.dao.IAbstractDao;
import pl.myo.dao.IOpinionDao;
import pl.myo.dao.IPatientDoctorRelationDao;
import pl.myo.dao.IUserDao;
import pl.myo.domain.User;
import pl.myo.domain.rel.PatientDoctorRelation;
import pl.myo.service.IPasswordService;
import pl.myo.service.IUserService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-22.
 */
@Service("userService")
public class UserServiceImpl extends AbstractServiceImpl<User> implements IUserService
{
   @Resource
   private IUserDao userDao;

   @Resource
   private IPasswordService passwordService;

   @Resource
   private IPatientDoctorRelationDao patientDoctorRelationDao;

   @Resource
   private IOpinionDao opinionDao;

   @Override
   protected IAbstractDao<User> getDao()
   {
      return userDao;
   }

   @Override
   @Transactional(readOnly = true)
   public User getByLogin(String login)
   {
      return userDao.getByLogin(login);
   }

   @Override
   @Transactional
   public void save(User user, String password)
   {
      user.setActive(true);
      userDao.save(user);
      passwordService.save(user, password);
   }

   @Override
   @Transactional
   public void update(User user, String password)
   {
      userDao.update(user);
      if (StringUtils.isNotBlank(password))
      {
         passwordService.changePassword(user, password);
      }
   }

   @Override
   @Transactional
   public void delete(User entity)
   {
      try
      {
         super.delete(entity);
      }
      catch (DataIntegrityViolationException | ConstraintViolationException e)
      {
         entity.setActive(false);
         update(entity);
      }
   }

   @Override
   @Transactional
   public int incrementDoctorPositive(User doctor)
   {
      return userDao.incrementDoctorPositive(doctor);
   }

   @Override
   @Transactional
   public int incrementDoctorNegative(User doctor)
   {
      return userDao.incrementDoctorNegative(doctor);
   }

   @Override
   @Transactional
   public int decrementDoctorPositive(User doctor)
   {
      return userDao.decrementDoctorPositive(doctor);
   }

   @Override
   @Transactional
   public int decrementDoctorNegative(User doctor)
   {
      return userDao.decrementDoctorNegative(doctor);
   }

   @Override
   @Transactional(readOnly = true)
   public Map<User, Boolean> getAsAssigmentMap(List<User> users, User doctor)
   {
      Map<User, Boolean> map = new HashMap<>();
      List<PatientDoctorRelation> relations = patientDoctorRelationDao.find(users, doctor);
      for (User user : users)
      {
         boolean value = !CollectionUtils.isEmpty(relations) && contains(user, relations);
         map.put(user, value);
      }
      return map;
   }

   private boolean contains(User patient, List<PatientDoctorRelation> relations)
   {
      for (PatientDoctorRelation relation : relations)
      {
         if (relation.getPatient().equals(patient))
         {
            return true;
         }
      }
      return false;
   }

   @Override
   @Transactional
   public void assing(User patient, User doctor)
   {
      if (!isAssigned(patient, doctor))
      {
         patientDoctorRelationDao.save(new PatientDoctorRelation(patient, doctor));
      }
   }

   @Override
   @Transactional
   public void unassing(User patient, User doctor)
   {
      PatientDoctorRelation relation = patientDoctorRelationDao.getBy(patient, doctor);
      patientDoctorRelationDao.delete(relation);
   }

   @Override
   @Transactional(readOnly = true)
   public List<User> getLastOpinioned()
   {
      return userDao.getLastOpinioned();
   }

   @Override
   @Transactional(readOnly = true)
   public boolean isAssigned(User patient, User doctor)
   {
      return patientDoctorRelationDao.getBy(patient, doctor) != null;
   }
}
