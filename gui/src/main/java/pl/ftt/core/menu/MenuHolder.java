package pl.ftt.core.menu;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.scanner.IPackageScanner;
import pl.ftt.domain.UserDetails;
import pl.ftt.service.ISecurityService;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuHolder implements Serializable
{
   private static final long serialVersionUID = -3588951236644938303L;

   private static List<MenuItem> menuItems;

   private static final MenuHolder INSTANCE = new MenuHolder();

   @SpringBean
   private IPackageScanner packageScanner;

   @SpringBean
   private ISecurityService securityService;

   private MenuHolder()
   {
      Injector.get().inject(this);
   }

   public void scanPackages(String... packagesToScan)
   {
      List<Class> menuClasses = packageScanner.scanPackages(MenuElement.class, true, packagesToScan);
      List<MenuItem> result = findMenuItems(menuClasses, EmptyParent.class);
      menuItems = Collections.unmodifiableList(result);
   }

   private List<MenuItem> findMenuItems(List<Class> menuClasses, Class parent)
   {
      List<MenuItem> result = new ArrayList<MenuItem>();
      for (Class clazz : menuClasses)
      {
         if (WebPage.class.isAssignableFrom(clazz))
         {
            MenuElement menuElement = (MenuElement) clazz.getAnnotation(MenuElement.class);
            MountPath mountPath = (MountPath) clazz.getAnnotation(MountPath.class);
            if (menuElement.parent() == parent)
            {
               String[] authorities = getAuthorities(clazz);

               String url = mountPath != null ? mountPath.value() : null;
               MenuItem menuItem = new MenuItem(
                     menuElement.resourceKey(),
                     clazz,
                     url,
                     menuElement.iconName(),
                     menuElement.index(),
                     authorities);
               result.add(menuItem);

               List<MenuItem> menuSubItems = findMenuItems(menuClasses, clazz);
               if (!CollectionUtils.isEmpty(menuSubItems))
               {
                  menuItem.setSubMenuItems(menuSubItems);
               }
            }
         }
      }
      Collections.sort(result, MenuItemByIndex.getInstance());
      return result;
   }

   protected String[] getAuthorities(Class clazz)
   {
      String[] authorities = null;
      Annotation annotation = clazz.getAnnotation(AuthorizeInstantiation.class);
      if (annotation != null)
      {
         AuthorizeInstantiation authorizeInstantiation = (AuthorizeInstantiation) annotation;
         authorities = authorizeInstantiation.value();
      }
      return authorities;
   }

   public List<MenuItem> getFiltered()
   {
      UserDetails userDetails = securityService.getUserDetails();
      List<MenuItem> filtered = doFilter(menuItems, userDetails);
      return filtered;
   }

   private List<MenuItem> doFilter(List<MenuItem> menuItems, UserDetails userDetails)
   {
      List<MenuItem> list = new ArrayList<MenuItem>();
      if (menuItems != null)
      {
         for (MenuItem menuItem : menuItems)
         {
            if (isAuthorized(menuItem, userDetails))
            {
               MenuItem cloned = SerializationUtils.clone(menuItem);

               List<MenuItem> subMenuItems = doFilter(menuItem.getSubMenuItems(), userDetails);
               cloned.setSubMenuItems(subMenuItems);

               boolean abstractTarget = Modifier.isAbstract(cloned.getTarget().getModifiers());
               if (abstractTarget && !subMenuItems.isEmpty()) // jesli parent jest abstract to nie pokazuj calego pl.ftt.core.menu
               {
                  list.add(cloned);
               }
               else if (!abstractTarget)
               {
                  list.add(cloned);
               }
            }
         }
      }
      return list;
   }

   protected boolean isAuthorized(MenuItem menuItem, UserDetails userDetails)
   {
      return isTypeAuthorized(menuItem, userDetails);
   }

  protected boolean isTypeAuthorized(MenuItem menuItem, UserDetails userDetails)
   {
      Class clazz = menuItem.getTarget();
      AuthorizeType authorizeType = (AuthorizeType) clazz.getAnnotation(AuthorizeType.class);
      if (authorizeType != null)
      {
         if (userDetails != null)
         {
            return (userDetails.isAdministrator() && authorizeType.administrator())
                    || (userDetails.isUser() && authorizeType.user());
         }
         else
         {
            return authorizeType.guest();
         }
      }
      return true;
   }

   public static MenuHolder getInstance()
   {
      return INSTANCE;
   }

   public MenuItem getMenuTarget()
   {
      List<MenuItem> menuItems = MenuHolder.getInstance().getFiltered();
      MenuItem menuItem = visitChildren(menuItems);
      if (menuItem != null)
      {
         return menuItem;
      }
      else
      {
         return null;
      }
   }

   public MenuItem visitChildren(List<MenuItem> menuItems)
   {
      for (MenuItem menuItem : menuItems)
      {
         if (!StringUtils.isBlank(menuItem.getTargetUrl()))
         {
            return menuItem;
         }
         else if (!menuItem.getSubMenuItems().isEmpty())
         {
            return visitChildren(menuItem.getSubMenuItems());
         }
      }
      return null;
   }
}