package pl.myo.dao;

import pl.myo.domain.User;
import pl.myo.domain.rel.PatientDoctorRelation;

import java.util.List;

/**
 * Created by Marek on 2015-06-04.
 */
public interface IPatientDoctorRelationDao extends IAbstractDao<PatientDoctorRelation>
{
   List<PatientDoctorRelation> find(List<User> users, User doctor);

   PatientDoctorRelation getBy(User patient, User doctor);
}
