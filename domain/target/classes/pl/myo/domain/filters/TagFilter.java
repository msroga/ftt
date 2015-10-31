package pl.myo.domain.filters;

/**
 * Created by Marek on 2015-05-27.
 */
public class TagFilter implements IFilter
{
   public static final String FIELD_NAME = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
}
