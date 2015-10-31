package pl.ftt.core.component.table.toolbar;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import pl.ftt.core.component.table.DataTable;

import java.util.Arrays;
import java.util.List;

public class NavigationToolbar extends Panel
{
   private static final List<Long> DEFAULT_CHOICES = Arrays.asList(5L ,10L, 25L, 50L, 100L);

   private static final String DEFAULT_NAVIGATION_RESOURCE_KEY = "CustomNavigatorLabel";

   private Long selected;

   private final String navigationResourceKey;

   public NavigationToolbar(String id, final DataTable<?> table)
   {
      this(id, table, DEFAULT_CHOICES, DEFAULT_NAVIGATION_RESOURCE_KEY);
   }

   public NavigationToolbar(String id, final DataTable<?> table, String navigationResourceKey)
   {
      this(id, table, DEFAULT_CHOICES, navigationResourceKey);
   }

   public NavigationToolbar(String id, final DataTable<?> table, List<Long> choices, String navigationResourceKey)
   {
      super(id);
      this.navigationResourceKey = navigationResourceKey;
      add(newNavigationLabel("navigationLabel", table));
      add(newItemsPerPageToolbar("itemsPerPageToolbar", table, choices));
      add(newPagingNavigator("paginationToolbar", table));
      selected = table.getItemsPerPage();
   }

   protected Component newItemsPerPageToolbar(String id, final DataTable<?> table, List<Long> choices)
   {
      WebMarkupContainer container = new WebMarkupContainer(id);
      DropDownChoice<Long> dropDownChoice = new DropDownChoice<Long>("itemsPerPageDropDown", new PropertyModel<Long>(
            this,
            "selected"), choices)
      {
         @Override
         public void onConfigure()
         {
            super.onConfigure();
            setModelObject(table.getItemsPerPage());
         }
      };

      dropDownChoice.add(new AjaxFormComponentUpdatingBehavior("change")
      {
         private static final long serialVersionUID = -3456772446686602298L;

         @Override
         protected void onUpdate(AjaxRequestTarget target)
         {
            table.setItemsPerPage(selected);
            table.refresh(target);
         }
      });
      container.add(dropDownChoice);
      return container;
   }

   protected Component newNavigationLabel(String id, final DataTable<?> table)
   {
      return new NavigationLabel("navigationLabel", table, navigationResourceKey);
   }

   protected PagingNavigator newPagingNavigator(String id, final DataTable<?> table)
   {
      return new pl.ftt.core.component.table.toolbar.PagingNavigator("paginationToolbar", table)
      {
         protected void onAjaxEvent(AjaxRequestTarget target)
         {
            table.refresh(target);
         }
      };
   }
}
