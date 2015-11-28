package pl.ftt.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import pl.ftt.domain.utils.UserTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "\"user\"")
////@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends AbstractEntity
{
   public static final String FIELD_FIRST_NAME = "firstName";

   public static final String FIELD_LAST_NAME = "lastName";

   public static final String FIELD_LOGIN = "login";

   public static final String FIELD_EMAIL = "email";

   public static final String FIELD_TYPE = "type";

   public static final String FIELD_ACTIVE = "active";

   public static final int FIELD_LOGIN_LENGTH = 32;

   public static final int FIELD_FIRST_NAME_LENGTH = 32;

   public static final int FIELD_LAST_NAME_LENGTH = 32;

   public static final int FIELD_EMAIL_LENGTH = 128;

   @Column(nullable = false, length = FIELD_LOGIN_LENGTH, unique = true)
   @Length(max = FIELD_LOGIN_LENGTH)
   @NotBlank
   private String login;

   @Column(nullable = false, name = "first_name", length = FIELD_FIRST_NAME_LENGTH)
   @Length(max = FIELD_FIRST_NAME_LENGTH)
   @NotBlank
   private String firstName;

   @Column(nullable = false, name = "last_name", length = FIELD_LAST_NAME_LENGTH)
   @Length(max = FIELD_LAST_NAME_LENGTH)
   @NotBlank
   private String lastName;

   @Column(nullable = false, length = FIELD_LOGIN_LENGTH, unique = true)
   @Length(max = FIELD_EMAIL_LENGTH)
   @NotBlank
   private String email;

   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   @NotNull
   private UserTypeEnum type;

   @Column(nullable = false)
   private boolean active;

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
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

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }
}
