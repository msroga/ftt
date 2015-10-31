package pl.myo.core.menu;

import java.util.Comparator;

public final class MenuItemByIndex implements Comparator<MenuItem>
{
   private static final MenuItemByIndex INSTANCE = new MenuItemByIndex();

   private MenuItemByIndex()
   {
   }

   @Override
   public int compare(MenuItem o1, MenuItem o2)
   {
      return o1.getIndex() - o2.getIndex();
   }

   public static MenuItemByIndex getInstance()
   {
      return INSTANCE;
   }
}
