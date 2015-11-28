package pl.ftt.gui.administration;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.gui.newConnetion.NewConnectionPage;

/**
 * Created by Marek on 2015-11-24.
 */
@MountPath(FttApiMappings.PRIVATE_ADMIN_PAGE)
@AuthorizeType(administrator = true)
@MenuElement(resourceKey = "menu.administration", iconName = "fa-cogs", index = 900)
public class AdminPage extends AbstractMenuPage
{
   public AdminPage()
   {
      AjaxLink ajaxLink = new AjaxLink("ajaxLink")
      {
         @Override
         public void onClick(AjaxRequestTarget ajaxRequestTarget)
         {
            setResponsePage(NewConnectionPage.class);
         }
      };
      bodyContainer.add(ajaxLink);
   }
}
