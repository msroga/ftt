package pl.ftt.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "\"password\"")
public class Password extends AbstractEntity
{
   public static final String FIELD_PASSWORD = "password";

   public static final String FIELD_USER = "user";

   public static final int FIELD_PASSWORD_LENGTH = 256;

   @OneToOne(optional = false)
   @JoinColumn(name = "user_id")
   @NotNull
   private User user;

   @Column(nullable = false, length = FIELD_PASSWORD_LENGTH)
   @Length(max = FIELD_PASSWORD_LENGTH)
   @NotBlank
   private String password;

   public User getUser()
   {
      return user;
   }

   public void setUser(User user)
   {
      this.user = user;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }
}
