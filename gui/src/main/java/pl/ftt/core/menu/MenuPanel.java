package pl.ftt.core.menu;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

public class MenuPanel extends Panel
{
   public MenuPanel(String id, final List<MenuItem> menuItems)
   {
      super(id);
      RepeatingView repeatingView = new RepeatingView("menuRepeater")
      {
         @Override
         protected void onPopulate()
         {
            removeAll();
            for (final MenuItem menuItem : menuItems)
            {
               AbstractMenuItemPanel menuItemPanel;
               if (!menuItem.getSubMenuItems().isEmpty())
               {
                  menuItemPanel = new SubMenuItemsPanel(newChildId(), menuItem);
               }
               else
               {
                  menuItemPanel = new MenuItemPanel(newChildId(), menuItem);
               }
               add(menuItemPanel);

               if (getPage().getClass().equals(menuItem.getTarget()))
               {
                  menuItemPanel.add(AttributeAppender.append("class", "active"));
               }
            }
         }
      };
      add(repeatingView);
   }
}
