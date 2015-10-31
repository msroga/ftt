package pl.ftt.gui.home;


import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.pages.AbstractMenuPage;

@MountPath(FttApiMappings.HOME_PAGE)
@MenuElement(resourceKey = "menu.administration.home", iconName = "fa-home", index = 10)
public class HomePage extends AbstractMenuPage
{
   public HomePage()
   {
   }
}
