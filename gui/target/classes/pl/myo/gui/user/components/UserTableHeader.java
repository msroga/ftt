package pl.myo.gui.user.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import pl.myo.core.component.table.SimpleTableHeader;

/**
 * Created by Marek on 2015-05-26.
 */
public abstract class UserTableHeader extends SimpleTableHeader
{
   public UserTableHeader(String id, IModel<String> caption)
   {
      this(id, caption, "fa fa-table");
   }

   public UserTableHeader(String id, IModel<String> caption, String icon)
   {
      super(id, caption, icon);

      AjaxLink addUserLink = new AjaxLink("addUserLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onAddUser(target);
         }
      };
      add(addUserLink);
   }

   protected abstract void onAddUser(AjaxRequestTarget target);
}
