package pl.myo.core.component.table.toolbar;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.navigation.paging.IPageable;

public class PagingNavigator extends AjaxPagingNavigator
{
   public PagingNavigator(final String id, final IPageable pageable)
   {
      super(id, pageable);
   }

}
