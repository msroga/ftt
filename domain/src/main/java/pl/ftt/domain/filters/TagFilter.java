package pl.ftt.domain.filters;

/**
 * Created by Marek on 2015-12-19.
 */
public class TagFilter implements IFilter
{
   public static final String FILTER_NAME = "name";

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
