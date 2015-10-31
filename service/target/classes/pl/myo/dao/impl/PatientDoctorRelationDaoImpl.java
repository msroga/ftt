package pl.myo.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.myo.dao.IPatientDoctorRelationDao;
import pl.myo.domain.User;
import pl.myo.domain.rel.PatientDoctorRelation;

import java.util.List;

/**
 * Created by Marek on 2015-06-04.
 */
@Repository("patientDoctorRelationDao")
public class PatientDoctorRelationDaoImpl extends AbstractDaoImpl<PatientDoctorRelation> implements
        IPatientDoctorRelationDao
{
   @Override
   public List<PatientDoctorRelation> find(List<User> users, User doctor)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(PatientDoctorRelation.FIELD_DOCTOR, doctor));
      criteria.add(Restrictions.in(PatientDoctorRelation.FIELD_PATIENT, users));
      return criteria.list();
   }

   @Override
   public PatientDoctorRelation getBy(User patient, User doctor)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(PatientDoctorRelation.FIELD_DOCTOR, doctor));
      criteria.add(Restrictions.eq(PatientDoctorRelation.FIELD_PATIENT, patient));
      return (PatientDoctorRelation) criteria.uniqueResult();
   }
}
