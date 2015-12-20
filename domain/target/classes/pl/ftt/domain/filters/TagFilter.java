package pl.ftt.domain.filters;

/**
 * Created by Marek on 2015-12-19.
 */
public class TagFilter implements IFilter
{
   public static final String FILTER_NAME = "name";

   public static final String FILTER_ACTIVE = "active";

   private String name;

   private Boolean active;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
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
