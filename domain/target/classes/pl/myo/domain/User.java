package pl.myo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "\"user\"")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQueries(
        {
                @NamedQuery(name = User.QUERY_INCREMENT_DOCTOR_POSITIVE_COUNTER, query = "update User set doctorPositiveCounter=doctorPositiveCounter + 1 where id = (:id)"),
                @NamedQuery(name = User.QUERY_INCREMENT_DOCTOR_NEGATIVE_COUNTER, query = "update User set doctorNegativeCounter=doctorNegativeCounter + 1 where id = (:id)"),
                @NamedQuery(name = User.QUERY_DECREMENT_DOCTOR_POSITIVE_COUNTER, query = "update User set doctorPositiveCounter=doctorPositiveCounter - 1 where id = (:id)"),
                @NamedQuery(name = User.QUERY_DECREMENT_DOCTOR_NEGATIVE_COUNTER, query = "update User set doctorNegativeCounter=doctorNegativeCounter - 1 where id = (:id)")
        })
public class User extends AbstractEntity
{
   public static final String QUERY_INCREMENT_DOCTOR_POSITIVE_COUNTER = "query.increment.doctor.positive.counter";

   public static final String QUERY_INCREMENT_DOCTOR_NEGATIVE_COUNTER = "query.increment.doctor.negative.counter";

   public static final String QUERY_DECREMENT_DOCTOR_POSITIVE_COUNTER = "query.decrement.doctor.positive.counter";

   public static final String QUERY_DECREMENT_DOCTOR_NEGATIVE_COUNTER = "query.decrement.doctor.negative.counter";

   public static final String FIELD_NAME = "name";

   public static final String FIELD_SURNNAME = "surname";

   public static final String FIELD_LOGIN = "login";

   public static final String FIELD_EMAIL = "email";

   public static final String FIELD_TYPE = "type";

   public static final String FIELD_SOCIAL_NUMBER = "socialNumber";

   public static final String FIELD_GENDER = "gender";

   public static final String FIELD_ACTIVE = "active";

   public static final String FIELD_DOCTOR_POSITIVE_COUNTER = "doctorPositiveCounter";

   public static final String FIELD_DOCTOR_NAGATIVE_COUNTER = "doctorNegativeCounter";

   public static final String FIELD_CREATED_DATE = "createdDate";

   public static final int FIELD_LOGIN_LENGTH = 32;

   public static final int FIELD_NAME_LENGTH = 32;

   public static final int FIELD_SURNAME_LENGTH = 32;

   public static final int FIELD_EMAIL_LENGTH = 128;

   public static final int FIELD_SOCIAL_NUMBER_LENGTH = 16;

   @Column(nullable = false, length = FIELD_LOGIN_LENGTH, unique = true)
   @Length(max = FIELD_LOGIN_LENGTH)
   @NotBlank
   private String login;

   @Column(nullable = false, name = "first_name", length = FIELD_LOGIN_LENGTH)
   @Length(max = FIELD_NAME_LENGTH)
   @NotBlank
   private String name;

   @Column(nullable = false, name = "last_name", length = FIELD_LOGIN_LENGTH)
   @Length(max = FIELD_SURNAME_LENGTH)
   @NotBlank
   private String surname;

   @Column(nullable = false, length = FIELD_LOGIN_LENGTH, unique = true)
   @Length(max = FIELD_EMAIL_LENGTH)
   @NotBlank
   private String email;

   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   @NotNull
   private UserTypeEnum type;

   @Column(nullable = true, name = "social_number", length = FIELD_LOGIN_LENGTH)
   @Length(max = FIELD_SOCIAL_NUMBER_LENGTH)
   private String socialNumber;

   @Column(nullable = true)
   private Boolean gender; //true - male, false - female

   @Column(nullable = false)
   private boolean active;

   @Column(name = "doctor_positive_counter")
   private int doctorPositiveCounter;

   @Column(name = "doctor_negative_counter")
   private int doctorNegativeCounter;

   @Column(name = "created_date", nullable = false)
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
   @NotNull
   private DateTime createdDate;

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getSurname()
   {
      return surname;
   }

   public void setSurname(String surname)
   {
      this.surname = surname;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public UserTypeEnum getType()
   {
      return type;
   }

   public void setType(UserTypeEnum type)
   {
      this.type = type;
   }

   public String getSocialNumber()
   {
      return socialNumber;
   }

   public void setSocialNumber(String socialNumber)
   {
      this.socialNumber = socialNumber;
   }

   public Boolean getGender()
   {
      return gender;
   }

   public void setGender(Boolean gender)
   {
      this.gender = gender;
   }

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }

   public boolean isAdministrator()
   {
      return type == UserTypeEnum.ADMIN;
   }

   public boolean isDoctor()
   {
      return type == UserTypeEnum.DOCTOR;
   }

   public boolean isUser()
   {
      return type == UserTypeEnum.USER;
   }

   public int getDoctorPositiveCounter()
   {
      return doctorPositiveCounter;
   }

   public void setDoctorPositiveCounter(int doctorPositiveCounter)
   {
      this.doctorPositiveCounter = doctorPositiveCounter;
   }

   public int getDoctorNegativeCounter()
   {
      return doctorNegativeCounter;
   }

   public void setDoctorNegativeCounter(int doctorNegativeCounter)
   {
      this.doctorNegativeCounter = doctorNegativeCounter;
   }

   public DateTime getCreatedDate()
   {
      return createdDate;
   }

   public void setCreatedDate(DateTime createdDate)
   {
      this.createdDate = createdDate;
   }
}
