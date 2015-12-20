package pl.ftt.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Marek on 2015-11-12.
 */
@Entity
@Table(name = "\"tag\"")
public class Tag extends AbstractEntity
{
   public static final String FIELD_ACTIVE = "active";

   public static final String FIELD_NAME = "name";

   public static final String FIELD_DESCRIPTION = "description";

   public static final int FIELD_NAME_LENGTH = 128;

   @Column(nullable = false, name = "name", length = FIELD_NAME_LENGTH)
   @Length(max = FIELD_NAME_LENGTH)
   @NotBlank
   private String name;

   @Column(nullable = true, name = "description")
   private String description;

   @Column(nullable = false, name = "active")
   private boolean active;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
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
