package pl.myo.core.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;

public class MenuItem implements Serializable
{
	private static final long serialVersionUID = -616209247133714353L;

	   private final String resourceKey;

	   private final Class<? extends WebPage> target;

	   private final String targetUrl;

	   private final String[] authorities;

	   private final int index;

	   private final String iconName;

	   private List<MenuItem> subMenuItems = new ArrayList<MenuItem>();

	   public MenuItem(String resourceKey, Class<? extends WebPage> target, String targetUrl, String iconName,
			   int index, String... authorities)
	   {
	      this.resourceKey = resourceKey;
	      this.target = target;
	      this.authorities = authorities;
	      this.index = index;
	      this.iconName = iconName;
	      this.targetUrl = targetUrl;
	   }

	   public Class getTarget()
	   {
	      return target;
	   }

	   public String getResourceKey()
	   {
	      return resourceKey;
	   }

	   public List<MenuItem> getSubMenuItems()
	   {
	      return subMenuItems;
	   }

	   public void addSubMenuItem(MenuItem... subMenuItems)
	   {
	      if (subMenuItems != null)
	      {
	         List<MenuItem> list = Arrays.asList(subMenuItems);
	         this.subMenuItems.addAll(list);
	      }
	   }

	   public String[] getAuthorities()
	   {
	      return authorities;
	   }

	   public int getIndex()
	   {
	      return index;
	   }

	   public void setSubMenuItems(List<MenuItem> subMenuItems)
	   {
	      this.subMenuItems = subMenuItems;
	   }

	   @Override
	   public String toString()
	   {
	      return resourceKey + " " + target.getSimpleName();
	   }

	   public String getIconName()
	   {
	      return iconName;
	   }

	   public String getTargetUrl()
	   {
	      return targetUrl;
	   }
}
