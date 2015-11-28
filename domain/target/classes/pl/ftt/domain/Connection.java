package pl.ftt.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-11-12.
 */
@Entity
@Table(name = "\"connection\"")
public class Connection extends AbstractEntity
{
   public static final String FIELD_ACTIVE = "active";

   public static final String FIELD_IDENTIFIER = "identifier";

   public static final String FIELD_COMMENT = "comment";

   public static final String FIELD_TYPE = "type";

   public static final int FIELD_IDENTIFIER_LENGTH = 16;

   @Column(nullable = false, name = "active")
   private boolean active;

   @Column(nullable = false, name = "identifier", length = FIELD_IDENTIFIER_LENGTH)
   @Length(max = FIELD_IDENTIFIER_LENGTH)
   @NotBlank
   private String identifier;

   @Column(nullable = true, name = "comment")
   private String comment;

   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   @NotNull
   private ConnectionTypeEnum type;

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }

   public String getIdentifier()
   {
      return identifier;
   }

   public void setIdentifier(String identifier)
   {
      this.identifier = identifier;
   }

   public String getComment()
   {
      return comment;
   }

   public void setComment(String comment)
   {
      this.comment = comment;
   }

   public ConnectionTypeEnum getType()
   {
      return type;
   }

   public void setType(ConnectionTypeEnum type)
   {
      this.type = type;
   }
}
