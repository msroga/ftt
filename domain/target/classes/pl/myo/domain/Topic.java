package pl.myo.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-05-28.
 */
@Entity
@Table(name = "topic")
public class Topic extends AbstractEntity
{
   public static final String FIELD_CREATED = "created";

   public static final String FIELD_USER = "user";

   public static final String FIELD_MESSAGE = "message";

   public static final String FIELD_TITLE = "title";

   public static final String FIELD_TYPE = "type";

   public static final String FIELD_ACTIVE = "active";

   @Column(name = "created", nullable = false)
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
   @NotNull
   private DateTime created;

   @ManyToOne(optional = false)
   @JoinColumn(name = "user_id")
   @NotNull
   private User user;

   @Column(name = "message", nullable = false)
   @NotNull
   private String message;

   @Column(name = "title", nullable = false)
   @NotNull
   private String title;

   @Column(name = "type", nullable = false)
   @Enumerated(EnumType.STRING)
   @NotNull
   private TopicTypeEnum type;

   @Column(nullable = false)
   private boolean active;

   public DateTime getCreated()
   {
      return created;
   }

   public void setCreated(DateTime created)
   {
      this.created = created;
   }

   public User getUser()
   {
      return user;
   }

   public void setUser(User user)
   {
      this.user = user;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public TopicTypeEnum getType()
   {
      return type;
   }

   public void setType(TopicTypeEnum type)
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
