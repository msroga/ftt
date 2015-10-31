package pl.ftt.core.renderer;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import pl.ftt.domain.AbstractEntity;
import pl.ftt.gui.formatter.Formatters;

public class DefaultChoiceRenderer<T extends AbstractEntity> implements IChoiceRenderer<T>
{
   private static final long serialVersionUID = 6260541879544140073L;

   private final Component component;

   public DefaultChoiceRenderer()
   {
      this(null);
   }

   public DefaultChoiceRenderer(Component component)
   {
      this.component = component;
   }

   @Override
   public Object getDisplayValue(T object)
   {
      return Formatters.format(object, component);
   }

   @Override
   public String getIdValue(T object, int index)
   {
      return Long.toString(object.getId());
   }
}
