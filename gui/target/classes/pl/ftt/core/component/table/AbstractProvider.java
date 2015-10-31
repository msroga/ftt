package pl.ftt.core.component.table;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import pl.ftt.domain.filters.IFilter;
import pl.ftt.domain.filters.OpenSearchDescription;
import pl.ftt.domain.filters.SortFilterChain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractProvider<T extends Serializable> extends SortableDataProvider<T, String> implements
      IFilterStateLocator<OpenSearchDescription<T>>
{
   private static final long serialVersionUID = 3656923947176480733L;

   protected OpenSearchDescription<T> osd;

   public AbstractProvider(OpenSearchDescription<T> osd)
   {
      this.osd = osd;
      if (osd.getSortFilterChain() != null)
      {
         SortFilterChain sortFilterChain = osd.getSortFilterChain();
         setSort(sortFilterChain.getField(), sortFilterChain.isAscending() ? SortOrder.ASCENDING : SortOrder.DESCENDING);
      }
   }

   @Override
   public Iterator<T> iterator(long first, long count)
   {
      return find(first, count).iterator();
   }

   public List<T> find(long first, long count)
   {
      SortFilterChain sortFilterChain = getSortFilterChain();
      osd.setSortFilterChain(sortFilterChain);
      return find(osd.getFilter(), sortFilterChain, (int) first, (int) count);
   }

   public SortFilterChain getSortFilterChain()
   {
      SortParam<String> sortParam = getSort();
      SortFilterChain sortFilterChain;
      if (sortParam != null)
      {
         sortFilterChain = new SortFilterChain(sortParam.getProperty(), sortParam.isAscending());
      }
      else
      {
         sortFilterChain = osd.getSortFilterChain();
      }
      return sortFilterChain;
   }

   public abstract List<T> find(IFilter filter, SortFilterChain sortFilterChain, int first, int count);

   public abstract long size(IFilter filter);

   @Override
   public long size()
   {
      return size(osd.getFilter());
   }

   @Override
   public IModel<T> model(T object)
   {
      return new Model<T>(object);
   }

   @Override
   public OpenSearchDescription<T> getFilterState()
   {
      return osd;
   }

   @Override
   public void setFilterState(OpenSearchDescription<T> osd)
   {
      this.osd = osd;
   }
}
