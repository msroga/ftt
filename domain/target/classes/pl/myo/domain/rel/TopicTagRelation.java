package pl.myo.domain.rel;

import pl.myo.domain.AbstractEntity;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-05-30.
 */
@Entity
@Table(name = "topic_tag_relation")
public class TopicTagRelation extends AbstractEntity
{
   public static final String FIELD_TOPIC = "topic";

   public static final String FIELD_TAG = "tag";

   @ManyToOne(optional = false)
   @JoinColumn(name = "topic_id")
   @NotNull
   private Topic topic;

   @ManyToOne(optional = false)
   @JoinColumn(name = "tag_id")
   @NotNull
   private Tag tag;

   public TopicTagRelation()
   {
      //nope
   }

   public TopicTagRelation(Topic topic, Tag tag)
   {
      this.topic = topic;
      this.tag = tag;
   }

   public Topic getTopic()
   {
      return topic;
   }

   public void setTopic(Topic topic)
   {
      this.topic = topic;
   }

   public Tag getTag()
   {
      return tag;
   }

   public void setTag(Tag tag)
   {
      this.tag = tag;
   }
}
