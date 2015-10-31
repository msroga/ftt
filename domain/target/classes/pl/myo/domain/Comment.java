package pl.myo.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-05-28.
 */
@Entity
@Table(name = "comment")
@NamedQueries(
{
       @NamedQuery(name = Comment.QUERY_INCREMENT_DOCTOR_LIKE_COUNTER, query = "update Comment set doctorLikeCounter=doctorLikeCounter + 1 where id = (:id)"),
       @NamedQuery(name = Comment.QUERY_INCREMENT_USER_LIKE_COUNTER, query = "update Comment set userLikeCounter=userLikeCounter + 1 where id = (:id)"),
       @NamedQuery(name = Comment.QUERY_DECREMENT_DOCTOR_LIKE_COUNTER, query = "update Comment set doctorLikeCounter=doctorLikeCounter - 1 where id = (:id)"),
       @NamedQuery(name = Comment.QUERY_DECREMENT_USER_LIKE_COUNTER, query = "update Comment set userLikeCounter=userLikeCounter - 1 where id = (:id)")
})
public class Comment extends AbstractEntity
{
   public static final String QUERY_INCREMENT_DOCTOR_LIKE_COUNTER = "increment.doctor.like,counter";

   public static final String QUERY_INCREMENT_USER_LIKE_COUNTER = "increment.user.like,counter";

   public static final String QUERY_DECREMENT_DOCTOR_LIKE_COUNTER = "decrement.doctor.like,counter";

   public static final String QUERY_DECREMENT_USER_LIKE_COUNTER = "decrement.user.like,counter";

   public static final String FIELD_CREATED = "created";

   public static final String FIELD_USER = "user";

   public static final String FIELD_MESSAGE = "message";

   public static final String FIELD_TOPIC = "topic";

   public static final String FIELD_DOCTOR_LIKE_COUNTER = "doctorLikeCounter";

   public static final String FIELD_USER_LIKE_COUNTER = "userLikeCounter";

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

   @ManyToOne(optional = false)
   @JoinColumn(name = "topic_id")
   @NotNull
   private Topic topic;

   @Column(name = "doctor_like_counter")
   private int doctorLikeCounter;

   @Column(name = "user_like_counter")
   private int userLikeCounter;

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

   public Topic getTopic()
   {
      return topic;
   }

   public void setTopic(Topic topic)
   {
      this.topic = topic;
   }

   public int getDoctorLikeCounter()
   {
      return doctorLikeCounter;
   }

   public void setDoctorLikeCounter(int doctorLikeCounter)
   {
      this.doctorLikeCounter = doctorLikeCounter;
   }

   public int getUserLikeCounter()
   {
      return userLikeCounter;
   }

   public void setUserLikeCounter(int userLikeCounter)
   {
      this.userLikeCounter = userLikeCounter;
   }
}
