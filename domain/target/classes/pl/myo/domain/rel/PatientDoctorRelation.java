package pl.myo.domain.rel;

import pl.myo.domain.AbstractEntity;
import pl.myo.domain.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-06-04.
 */
@Entity
@Table(name = "patient_doctor_relation")
public class PatientDoctorRelation extends AbstractEntity
{
   public static final String FIELD_PATIENT = "patient";

   public static final String FIELD_DOCTOR = "doctor";

   @ManyToOne(optional = false)
   @JoinColumn(name = "patient_id")
   @NotNull
   private User patient;

   @ManyToOne(optional = false)
   @JoinColumn(name = "doctor_id")
   @NotNull
   private User doctor;

   public PatientDoctorRelation()
   {
      //nope
   }

   public PatientDoctorRelation(User patient, User doctor)
   {
      this.patient = patient;
      this.doctor = doctor;
   }

   public User getPatient()
   {
      return patient;
   }

   public void setPatient(User patient)
   {
      this.patient = patient;
   }

   public User getDoctor()
   {
      return doctor;
   }

   public void setDoctor(User doctor)
   {
      this.doctor = doctor;
   }
}
