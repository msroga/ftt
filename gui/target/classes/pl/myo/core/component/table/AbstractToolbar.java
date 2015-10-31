package pl.myo.core.component.table;

import org.apache.wicket.markup.html.panel.Panel;
import pl.myo.domain.filters.IFilter;

public abstract class AbstractToolbar<T extends IFilter> extends Panel
{
   protected final DataTable<?> table;

   public AbstractToolbar(DataTable<?> table)
   {
      super(table.newToolbarId());
      this.table = table;
   }

   public DataTable<?> getTable()
   {
      return table;
   }
}
