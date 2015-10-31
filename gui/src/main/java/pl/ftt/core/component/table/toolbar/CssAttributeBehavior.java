package pl.ftt.core.component.table.toolbar;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;

public abstract class CssAttributeBehavior extends Behavior
{
   private static final long serialVersionUID = 1L;

   protected abstract String getCssClass();

   /**
    * @see org.apache.wicket.behavior.Behavior#onComponentTag(org.apache.wicket.Component, org.apache.wicket.markup.ComponentTag)
    */
   @Override
   public void onComponentTag(final Component component, final ComponentTag tag)
   {
      String className = getCssClass();
      if (!Strings.isEmpty(className))
      {
         tag.append("class", className, " ");
      }
   }
}
