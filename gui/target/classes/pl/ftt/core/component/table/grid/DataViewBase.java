package pl.ftt.core.component.table.grid;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import java.util.Iterator;

public abstract class DataViewBase<T> extends AbstractPageableView<T>
{
   private static final long serialVersionUID = 1L;

   private final IDataProvider<T> dataProvider;

   /**
    * @param id
    *            component id
    * @param dataProvider
    *            data provider
    */
   public DataViewBase(String id, IDataProvider<T> dataProvider)
   {
      super(id);

      this.dataProvider = Args.notNull(dataProvider, "dataProvider");
   }

   /**
    * @return data provider associated with this view
    */
   protected final IDataProvider<T> internalGetDataProvider()
   {
      return dataProvider;
   }

   @Override
   protected final Iterator<IModel<T>> getItemModels(long offset, long count)
   {
      return new ModelIterator<T>(internalGetDataProvider(), offset, count);
   }

   /**
    * Helper class that converts input from IDataProvider to an iterator over view items.
    * 
    * @author Igor Vaynberg (ivaynberg)
    * 
    * @param <T>
    *            Model object type
    */
   private static final class ModelIterator<T> implements Iterator<IModel<T>>
   {
      private final Iterator<? extends T> items;

      private final IDataProvider<T> dataProvider;

      private final long max;

      private long index;

      /**
       * Constructor
       * 
       * @param dataProvider
       *            data provider
       * @param offset
       *            index of first item
       * @param count
       *            max number of items to return
       */
      public ModelIterator(IDataProvider<T> dataProvider, long offset, long count)
      {
         this.dataProvider = dataProvider;
         max = count;

         items = count > 0 ? dataProvider.iterator(offset, count) : null;
      }

      /**
       * @see java.util.Iterator#remove()
       */
      @Override
      public void remove()
      {
         throw new UnsupportedOperationException();
      }

      /**
       * @see java.util.Iterator#hasNext()
       */
      @Override
      public boolean hasNext()
      {
         return items != null && items.hasNext() && (index < max);
      }

      /**
       * @see java.util.Iterator#next()
       */
      @Override
      public IModel<T> next()
      {
         index++;
         return dataProvider.model(items.next());
      }
   }

   @Override
   protected final long internalGetItemCount()
   {
      return internalGetDataProvider().size();
   }

   /**
    * @see org.apache.wicket.markup.repeater.AbstractPageableView#onDetach()
    */
   @Override
   protected void onDetach()
   {
      dataProvider.detach();
      super.onDetach();
   }
}
