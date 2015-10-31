package pl.myo.service.impl;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.myo.dao.IAbstractDao;
import pl.myo.dao.IOpinionDao;
import pl.myo.domain.Opinion;
import pl.myo.domain.OpinionTypeEnum;
import pl.myo.domain.User;
import pl.myo.service.IOpinionService;
import pl.myo.service.ISecurityService;
import pl.myo.service.IUserService;

import javax.annotation.Resource;

/**
 * Created by Marek on 2015-06-04.
 */
@Service("opinionService")
public class OpinionServiceImpl extends AbstractServiceImpl<Opinion> implements IOpinionService
{
   @Resource
   private IOpinionDao opinionDao;

   @Resource
   private ISecurityService securityService;

   @Resource
   private IUserService userService;

   @Override
   protected IAbstractDao<Opinion> getDao()
   {
      return opinionDao;
   }

   @Override
   @Transactional
   public Opinion save(Opinion opinion)
   {
      opinion.setCreated(DateTime.now());
      opinion.setAuthor(securityService.getLoggedInUser());
      if (opinion.getType() == OpinionTypeEnum.POSITIVE)
      {
         userService.incrementDoctorPositive(opinion.getDoctor());
      }
      else
      {
         userService.incrementDoctorNegative(opinion.getDoctor());
      }
      return super.save(opinion);
   }

   @Override
   @Transactional
   public void delete(Opinion opinion)
   {
      if (opinion.getType() == OpinionTypeEnum.POSITIVE)
      {
         userService.decrementDoctorPositive(opinion.getDoctor());
      }
      else
      {
         userService.decrementDoctorNegative(opinion.getDoctor());
      }
      super.delete(opinion);
   }

   @Override
   @Transactional(readOnly = true)
   public boolean isOpinioned(User author, User doctor)
   {
      long result = opinionDao.count(author, doctor);
      return result > 0;
   }
}
