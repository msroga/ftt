package pl.myo.core.component.table.toolbar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink.CssProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink.ICssProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import pl.myo.core.component.table.DataTable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HeadersToolbar<T extends Serializable> extends Panel
{
   private static final ICssProvider<String> CSS_PROVIDER = new CssProvider<String>(
         "sorting_asc",
         "sorting_desc",
         "sorting");

   private DataTable<T> dataTable;

   public HeadersToolbar(String id, final DataTable<T> dataTable, final ISortStateLocator<String> stateLocator)
   {
      super(id);
      this.dataTable = dataTable;

      RefreshingView<IColumn<T, String>> headers = new RefreshingView<IColumn<T, String>>("headers")
      {
         private static final long serialVersionUID = 1L;

         @Override
         protected Iterator<IModel<IColumn<T, String>>> getItemModels()
         {
            return HeadersToolbar.this.getItemModels();
         }

         @Override
         protected void populateItem(Item<IColumn<T, String>> item)
         {
            final IColumn<T, String> column = item.getModelObject();

            WebMarkupContainer header = null;

            if (column.isSortable())
            {
               header = newSortableHeader("header", column.getSortProperty(), stateLocator);
            }
            else
            {
               header = new SimpleHeader("header");
            }

            if (column instanceof IStyledColumn)
            {
               CssAttributeBehavior cssAttributeBehavior = new CssAttributeBehavior()
               {
                  private static final long serialVersionUID = 1L;

                  @Override
                  protected String getCssClass()
                  {
                     return ((IStyledColumn<?, String>) column).getCssClass();
                  }
               };

               header.add(cssAttributeBehavior);
            }

            item.add(header);
            item.setRenderBodyOnly(true);
            header.add(column.getHeader("label"));
         }
      };
      add(headers);
   }

   protected Iterator<IModel<IColumn<T, String>>> getItemModels()
   {
      List<IModel<IColumn<T, String>>> columnsModels = new LinkedList<IModel<IColumn<T, String>>>();

      for (IColumn<T, String> column : dataTable.getColumns())
      {
         columnsModels.add(Model.of(column));
      }

      return columnsModels.iterator();
   }

   protected WebMarkupContainer newSortableHeader(final String headerId, final String property,
         final ISortStateLocator<String> locator)
   {
      return new HeaderOrder(headerId, property, locator, CSS_PROVIDER)
      {
         @Override
         protected void onSortChanged(AjaxRequestTarget target)
         {
            super.onSortChanged(target);
            dataTable.setCurrentPage(0);
            dataTable.refresh(target);
            target.appendJavaScript("fixActionColumn();");
            target.appendJavaScript("fixSelectorColumn();");
            target.appendJavaScript("selectRow();");
            target.add(dataTable.getHeadersToolbar());
         }
      };
   }
}
