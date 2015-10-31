package pl.myo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import pl.myo.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tag extends AbstractEntity
{
   public static final String FIELD_NAME = "name";

   public static final String FIELD_ACTIVE = "active";

   public static final int FIELD_NAME_LENGTH = 64;

   @Column(nullable = false, length = FIELD_NAME_LENGTH, unique = true)
   @Length(max = FIELD_NAME_LENGTH)
   @NotBlank
   private String name;

   private boolean active;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
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
