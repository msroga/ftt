package pl.myo.domain.filters;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * It is data holder to storage information about filter, sort filter chain and list of objects.
 * User should define type of objects, what he want to hold in this class.
 * <p/>
 * <ul>
 * <li>
 * {@link #filter} - filter
 * </li>
 * <li>
 * {@link #sortFilterChain} - SortFilterChain
 * </li>
 * <li>
 * {@link #selected} - Selected elements
 * </li>
 * </ul>
 *
 * @param <T> type of holding objects
 */
public class OpenSearchDescription<T extends Serializable> implements Serializable
{
   private static final long serialVersionUID = 7592882384296888888L;

   public static final String FIELD_FILTER = "filter";

   public static final String FIELD_SORT_FILTER_CHAIN = "sortFilterChain";

   public static final String FIELD_SELECTED = "selected";

   /**
    * Filter
    */
   private IFilter filter;

   /**
    * SortFilterChain
    */
   private SortFilterChain sortFilterChain;

   /**
    * Selected elements
    */
   private List<T> selected = new ArrayList<T>();

   /**
    * Returns filter instance.
    *
    * @return {@link #filter} filter
    */
   public IFilter getFilter() {
      return filter;
   }

   /**
    * Sets filter
    *
    * @param filter filter to set
    */
   public void setFilter(IFilter filter) {
      this.filter = filter;
   }

   /**
    * Returns sort filter chain
    *
    * @return {@link #sortFilterChain} sortFilterChain
    */
   public SortFilterChain getSortFilterChain() {
      return sortFilterChain;
   }

   /**
    * Sets sort filter chain
    *
    * @param sortFilterChain sortFilterChain to set
    */
   public void setSortFilterChain(SortFilterChain sortFilterChain) {
      this.sortFilterChain = sortFilterChain;
   }

   /**
    * Returns selected elements.
    *
    * @return {@link #selected} selected elements
    */
   public List<T> getSelected() {
      return selected;
   }

   /**
    * Sets selected elements
    *
    * @param selected selected elements
    */
   public void setSelected(List<T> selected) {
      this.selected = selected;
   }

   /**
    * Overrides Object's toString().
    * toString() is a combination of {@link #filter}, {@link #sortFilterChain} and {@link #selected}
    *
    * @return string representation of this object
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this)
            .append(FIELD_FILTER, filter)
            .append(FIELD_SORT_FILTER_CHAIN, sortFilterChain)
            .append(FIELD_SELECTED, selected)
            .toString();
   }
}
