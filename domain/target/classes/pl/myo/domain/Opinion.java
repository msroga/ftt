package pl.myo.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-06-04.
 */
@Entity
@Table(name = "opinion")
public class Opinion extends AbstractEntity
{
   public static final String FIELD_CREATED = "created";

   public static final String FIELD_MESSAGE = "message";

   public static final String FIELD_TYPE = "type";

   public static final String FIELD_AUTHOR = "author";

   public static final String FIELD_DOCTOR = "doctor";

   @Column(name = "created", nullable = false)
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
   @NotNull
   private DateTime created;

   @Column(name = "message", nullable = false)
   @NotNull
   private String message;

   @Column(name = "type", nullable = false)
   @Enumerated(EnumType.STRING)
   @NotNull
   private OpinionTypeEnum type;

   @ManyToOne(optional = false)
   @JoinColumn(name = "author_id")
   @NotNull
   private User author;

   @ManyToOne(optional = false)
   @JoinColumn(name = "doctor_id")
   @NotNull
   private User doctor;

   public DateTime getCreated()
   {
      return created;
   }

   public void setCreated(DateTime created)
   {
      this.created = created;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }

   public OpinionTypeEnum getType()
   {
      return type;
   }

   public void setType(OpinionTypeEnum type)
   {
      this.type = type;
   }

   public User getAuthor()
   {
      return author;
   }

   public void setAuthor(User author)
   {
      this.author = author;
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
