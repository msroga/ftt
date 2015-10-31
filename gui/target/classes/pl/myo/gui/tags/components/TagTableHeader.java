package pl.myo.gui.tags.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import pl.myo.core.component.table.SimpleTableHeader;

/**
 * Created by Marek on 2015-05-27.
 */
public abstract class TagTableHeader extends SimpleTableHeader
{
   public TagTableHeader(String id, IModel<String> caption)
   {
      this(id, caption, "fa fa-table");
   }

   public TagTableHeader(String id, IModel<String> caption, String icon)
   {
      super(id, caption, icon);

      AjaxLink addTagLink = new AjaxLink("addTagLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onAddTag(target);
         }
      };
      add(addTagLink);
   }

   protected abstract void onAddTag(AjaxRequestTarget target);
}
