package pl.ftt.core.renderer;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * Created by Marek on 2015-12-20.
 */
public class ActiveChoiceRenderer implements IChoiceRenderer<Boolean>
{
   private Component component;

   public ActiveChoiceRenderer()
   {
      this(null);
   }

   public ActiveChoiceRenderer(Component component)
   {
      this.component = component;
   }

   @Override
   public Object getDisplayValue(Boolean object)
   {
      return Boolean.TRUE.equals(object) ? component.getString("active") : component.getString("inactive");
   }

   @Override
   public String getIdValue(Boolean object, int index)
   {
      return object.toString();
   }
}
