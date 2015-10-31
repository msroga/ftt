package pl.myo.core.component.table;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.IItemReuseStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import pl.myo.core.component.table.AbstractProvider;
import pl.myo.core.component.table.grid.AbstractPageableView;
import pl.myo.core.component.table.grid.DataGridView;
import pl.myo.core.component.table.toolbar.CssAttributeBehavior;
import pl.myo.core.component.table.toolbar.HeadersToolbar;
import pl.myo.core.component.table.toolbar.NavigationToolbar;

public class DataTable<T extends Serializable> extends Panel implements IPageableItems
{
   public static final int DEFAULT_ITEMS_PER_PAGE = 25;

   protected final AbstractPageableView<T> datagrid;

   protected final WebMarkupContainer content;

   protected final WebMarkupContainer body;

   protected final WebMarkupContainer table;

   protected final WebMarkupContainer widget;

   protected final AbstractProvider<T> provider;

   protected final List<? extends IColumn<T, String>> columns;

   protected final Panel caption;

   protected final Panel headersToolbar;

   protected final Panel navigationToolbar;

   private final RepeatingView toolbars;

   protected final boolean doubleClick;

   public DataTable(final String id, final List<? extends IColumn<T, String>> columns, final IModel<String> caption,
                    final AbstractProvider<T> provider)
   {
      this(id, columns, caption, provider, false);
   }

   public DataTable(final String id, final List<? extends IColumn<T, String>> columns, final IModel<String> caption,
                    final AbstractProvider<T> provider, boolean doubleClick)
   {
      this(id, columns, caption, provider, DEFAULT_ITEMS_PER_PAGE, doubleClick);
   }

   @Override
   public void renderHead(IHeaderResponse response)
   {
      super.renderHead(response);
//      response.render(OnDomReadyHeaderItem.forScript("$('#scrollablePanel').scroll(onDropdownScroll);"));
   }

   public DataTable(final String id, final List<? extends IColumn<T, String>> columns, final IModel<String> caption,
                    final AbstractProvider<T> provider, final long rowsPerPage)
   {
      this(id, columns, caption, provider, rowsPerPage, false);
   }

   public DataTable(final String id, final List<? extends IColumn<T, String>> columns, final IModel<String> caption,
                    final AbstractProvider<T> provider, final long rowsPerPage, boolean doubleClick)
   {
      super(id);

      Args.notEmpty(columns, "columns");

      this.columns = columns;
      this.caption = newCaption("header", caption);
      this.provider = provider;
      this.doubleClick = doubleClick;

      content = new WebMarkupContainer("content");

      widget = new WebMarkupContainer("widget");
      widget.setOutputMarkupId(true);
      widget.setMarkupId("wid-id-" + new Random().nextInt());
      widget.add(this.caption);

      body = new WebMarkupContainer("body");
      body.setOutputMarkupId(true);

      table = new WebMarkupContainer("table");
      table.setOutputMarkupId(true);
      table.add(body);

      content.add(table);
      widget.add(content);
      add(widget);

      headersToolbar = newHeadersToolbar("headersToolbar", provider);
      headersToolbar.setOutputMarkupId(true);
      table.add(headersToolbar);

      toolbars = new RepeatingView("toolbars");
      content.add(toolbars);

      datagrid = newDataGridView("rows", columns, provider);
      datagrid.setItemsPerPage(rowsPerPage);
      body.add(datagrid);

      navigationToolbar = newNavigationToolbar("navigationToolbar");
      navigationToolbar.setOutputMarkupId(true);
      content.add(navigationToolbar);
   }

   public final void setTableBodyCss(final String cssStyle)
   {
      body.add(AttributeModifier.replace("class", cssStyle));
   }

   public final WebMarkupContainer getBody()
   {
      return body;
   }

   /**
    * @return the user used for the table caption
    */
   public final Component getCaption()
   {
      return caption;
   }

   public final AbstractProvider<T> getDataProvider()
   {
      return provider;
   }

   public List<? extends IColumn<T, String>> getColumns()
   {
      return columns;
   }

   @Override
   public final long getCurrentPage()
   {
      return datagrid.getCurrentPage();
   }

   @Override
   public final long getPageCount()
   {
      return datagrid.getPageCount();
   }

   public final long getRowCount()
   {
      return datagrid.getRowCount();
   }

   @Override
   public final long getItemsPerPage()
   {
      return datagrid.getItemsPerPage();
   }

   @Override
   public final void setCurrentPage(final long page)
   {
      datagrid.setCurrentPage(page);
      onPageChanged();
   }

   public final DataTable<T> setItemReuseStrategy(final IItemReuseStrategy strategy)
   {
      datagrid.setItemReuseStrategy(strategy);
      return this;
   }

   public void setItemsPerPage(final long items)
   {
      datagrid.setItemsPerPage(items);
      onPageChanged();
   }

   @Override
   public long getItemCount()
   {
      return datagrid.getItemCount();
   }

   protected Panel newCaption(String id, IModel<String> caption)
   {
      return new SimpleTableHeader(id, caption);
   }

   protected Item<IColumn<T, String>> newCellItem(final String id, final int index,
                                                  final IModel<IColumn<T, String>> model)
   {
      return new Item<IColumn<T, String>>(id, index, model);
   }

   protected Item<T> newRowItem(final String id, final int index, final IModel<T> model)
   {
      final Item<T> item = new OddEvenItem<T>(id, index, model);
      item.setOutputMarkupId(true);
      item.add(new AjaxEventBehavior("dblclick")
      {
         @Override
         protected void onEvent(AjaxRequestTarget target)
         {
            DataTable.this.onRowClick(target, model);
         }

         @Override
         public boolean isEnabled(Component component)
         {
            return isDoubleClickEnabled(item.getModelObject());
         }
      });
      return item;
   }

   protected void onRowClick(AjaxRequestTarget target, IModel<T> model)
   {
      //to overide
   }

   public boolean isDoubleClickEnabled(T task)
   {
      return doubleClick;
   }

   protected Panel newHeadersToolbar(String id, ISortStateLocator<String> locator)
   {
      return new HeadersToolbar<T>(id, this, locator);
   }

   protected Panel newNavigationToolbar(String id)
   {
      return new NavigationToolbar(id, this);
   }

   protected AbstractPageableView<T> newDataGridView(String id, List<? extends IColumn<T, String>> columns,
                                                     AbstractProvider<T> provider)
   {
      return new DataGridView<T>(id, columns, provider)
      {
         private static final long serialVersionUID = 1L;

         @SuppressWarnings(
                 {
                         "rawtypes", "unchecked"
                 })
         @Override
         protected Item newCellItem(final String id, final int index, final IModel model)
         {
            Item item = DataTable.this.newCellItem(id, index, model);
            final IColumn<T, String> column = DataTable.this.columns.get(index);
            if (column instanceof IStyledColumn)
            {
               item.add(new CssAttributeBehavior()
               {
                  private static final long serialVersionUID = 1L;

                  @Override
                  protected String getCssClass()
                  {
                     return ((IStyledColumn<T, String>) column).getCssClass();
                  }
               });
            }
            return item;
         }

         @Override
         protected Item<T> newRowItem(final String id, final int index, final IModel<T> model)
         {
            return DataTable.this.newRowItem(id, index, model);
         }
      };
   }

   @Override
   protected void onDetach()
   {
      super.onDetach();

      for (IColumn<T, String> column : columns)
      {
         column.detach();
      }
   }

   public void onPageChanged()
   {
      // noop
   }

   public WebMarkupContainer getContent()
   {
      return content;
   }

   public WebMarkupContainer getTable()
   {
      return table;
   }

   public WebMarkupContainer getWidget()
   {
      return widget;
   }

   public Panel getHeadersToolbar()
   {
      return headersToolbar;
   }

   public Panel getNavigationToolbar()
   {
      return navigationToolbar;
   }

   String newToolbarId()
   {
      return toolbars.newChildId();
   }

   public void addToolbar(AbstractToolbar toolbar)
   {
      toolbars.add(toolbar);
   }

   public void refresh(AjaxRequestTarget target)
   {
      target.add(body);
      target.add(navigationToolbar);
      target.add(headersToolbar);
   }

   public void refreshItem(AjaxRequestTarget target, T entity)
   {
      Item<T> item = getRowItem(entity);
      if (item != null)
      {
         target.add(item);
      }
   }

   public void refreshItem(AjaxRequestTarget target, IModel<T> model)
   {
      Item<T> item = getRowItem(model);
      if (item != null)
      {
         target.add(item);
      }
   }

   public Item<T> getRowItem(T entity)
   {
      IModel<T> model = provider.model(entity);
      Item<T> item = getRowItem(model);
      model.detach();
      return item;
   }

   public Item<T> getRowItem(final IModel<T> model)
   {
      return visitChildren(Item.class, new IVisitor<Item<T>, Item>()
      {
         @Override
         public void component(Item<T> item, IVisit<Item> visit)
         {
            if (item.getDefaultModel() != null && item.getDefaultModel().equals(model))
            {
               visit.stop(item);
            }
         }
      });
   }

   public RepeatingView getToolbars()
   {
      return toolbars;
   }
}
