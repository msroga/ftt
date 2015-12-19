package pl.ftt.gui.tags.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.toolbar.FilterToolbar;
import pl.ftt.domain.filters.TagFilter;

/**
 * Created by Marek on 2015-12-19.
 */
public abstract class TagFilterToolbar extends FilterToolbar<TagFilter>
{
   public TagFilterToolbar(DataTable<?> table)
   {
      super(table);

      AjaxLink addLink = new AjaxLink("addLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onAdd(target);
         }
      };
      form.add(addLink);
   }

   protected abstract void onAdd(AjaxRequestTarget target);

   @Override
   public void registerFilters(DataTable<?> table, IModel<TagFilter> filterModel)
   {
       TextField<String> loginFilter = new TextField<String>("nameFilter", new PropertyModel<String>(filterModel, TagFilter.FILTER_NAME));
       addFilter(loginFilter, new ResourceModel("tag.name"));
   }
}
