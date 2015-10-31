package pl.myo.core.component.table.grid;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.util.List;

public class DataGridView<T> extends AbstractDataGridView<T>
{
   private static final long serialVersionUID = 1L;

   /**
    * Constructor
    * 
    * Notice cells are created in the same order as cell populators in the list
    * 
    * @param id
    *            component id
    * @param populators
    *            list of ICellPopulators used to populate cells
    * @param dataProvider
    *            data provider
    */
   public DataGridView(final String id, final List<? extends ICellPopulator<T>> populators,
         final IDataProvider<T> dataProvider)
   {
      super(id, populators, dataProvider);
   }

   /**
    * Returns the list of cell populators
    * 
    * @return the list of cell populators
    */
   public List<? extends ICellPopulator<T>> getPopulators()
   {
      return internalGetPopulators();
   }

   /**
    * Returns the data provider
    * 
    * @return data provider
    */
   public IDataProvider<T> getDataProvider()
   {
      return internalGetDataProvider();
   }
}
