package pl.myo.core.component.table.toolbar;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink.ICssProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.Strings;

public class HeaderOrder extends Panel
{
   public HeaderOrder(final String id, final String property, final ISortStateLocator<String> stateLocator,
         final ICssProvider<String> provider)
   {
      super(id);
      add(new AjaxEventBehavior("onclick")
      {
         private static final long serialVersionUID = 7672021482216074145L;

         @Override
         protected void onEvent(AjaxRequestTarget target)
         {
            ISortState<String> state = stateLocator.getSortState();
            SortOrder order = state.getPropertySortOrder(property);
            state.setPropertySortOrder(property, nextSortOrder(order));
            onSortChanged(target);
         }

         @Override
         protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
         {
            super.updateAjaxAttributes(attributes);
            attributes.setAllowDefault(false);
         }

         @Override
         protected void onComponentTag(ComponentTag tag)
         {
            super.onComponentTag(tag);

            ISortState<String> sortState = stateLocator.getSortState();
            String cssClass = provider.getClassAttributeValue(sortState, property);
            if (!Strings.isEmpty(cssClass))
            {
               tag.append("class", cssClass, " ");
            }
         }
      });
   }

   protected void onSortChanged(AjaxRequestTarget target)
   {
      // noop
   }

   protected SortOrder nextSortOrder(final SortOrder order)
   {
      if (order == SortOrder.NONE)
      {
         return SortOrder.ASCENDING;
      }
      else
      {
         return order == SortOrder.ASCENDING ? SortOrder.DESCENDING : SortOrder.ASCENDING;
      }
   }

}
