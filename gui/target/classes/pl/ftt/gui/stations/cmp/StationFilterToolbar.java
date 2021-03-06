package pl.ftt.gui.stations.cmp;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.toolbar.FilterToolbar;
import pl.ftt.core.renderer.ActiveChoiceRenderer;
import pl.ftt.domain.filters.StationFilter;

import java.util.Arrays;

/**
 * Created by Marek on 2015-12-05.
 */
public abstract class StationFilterToolbar extends FilterToolbar<StationFilter>
{
   public StationFilterToolbar(DataTable<?> table)
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

   @Override
   public void registerFilters(DataTable<?> table, IModel<StationFilter> filterModel)
   {
      TextField<String> loginFilter = new TextField<String>("nameFilter", new PropertyModel<String>(filterModel, StationFilter.FILTER_NAME));
      addFilter(loginFilter, new ResourceModel("station.name"));


      DropDownChoice activeFilter = new DropDownChoice<Boolean>(
              "activeFilter",
              new PropertyModel<Boolean>(filterModel, StationFilter.FILTER_ACTIVE),
              Arrays.asList(Boolean.TRUE, Boolean.FALSE));
      activeFilter.setChoiceRenderer(new ActiveChoiceRenderer(form));
      addFilter(activeFilter, new ResourceModel("station.active"));
   }

   protected abstract void onAdd(AjaxRequestTarget target);
}
