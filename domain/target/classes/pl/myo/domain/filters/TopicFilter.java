package pl.myo.domain.filters;

import pl.myo.domain.Tag;
import pl.myo.domain.TopicTypeEnum;

import java.util.List;

/**
 * Created by Marek on 2015-05-30.
 */
public class TopicFilter implements IFilter
{
   public static final String FIELD_TYPE = "type";

   public static final String FIELD_TITLE = "title";

   public static final String FIELD_TAGS = "tags";

   public static final String FIELD_ACTIVE = "active";

   private TopicTypeEnum type;

   private String title;

   private List<Tag> tags;

   private Boolean active = Boolean.TRUE;

   public TopicTypeEnum getType()
   {
      return type;
   }

   public void setType(TopicTypeEnum type)
   {
      this.type = type;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public List<Tag> getTags()
   {
      return tags;
   }

   public void setTags(List<Tag> tags)
   {
      this.tags = tags;
   }

   public Boolean getActive()
   {
      return active;
   }

   public void setActive(Boolean active)
   {
      this.active = active;
   }
}
