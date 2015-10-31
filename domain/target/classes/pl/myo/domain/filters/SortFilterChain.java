package pl.myo.domain.filters;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Manage sorting criteria.</br>
 * User should defined here name of the property after which he wants to sort the result and the sorting direction.
 * In case of more compound sorting use intern SortFilterChain to defined chain of next level sorting.
 * <ul>
 * <li>
 * {@link #field} is a field after which will be sort.
 * </li>
 * <li>
 * {@link #ascending} is a field to know how to sort.
 * </li>
 * <li>
 * {@link #next} may be next SortFilterChain - {@link #append(SortFilterChain)}.
 * </li>
 * </ul>
 */
public class SortFilterChain implements Serializable
{
   private static final long serialVersionUID = -7625899492348081465L;

   public static final String FIELD_FIELD = "field";

   public static final String FIELD_ASCENDING = "ascending";

   public static final String FIELD_NEXT = "next";

   /**
    * Field after which will be sort.
    */
   private String field;

   /**
    * Is a field to know how to sort.
    */
   private boolean ascending = true;

   /**
    * May be next SortFilterChain - {@link #append(SortFilterChain)}.
    */
   private SortFilterChain next;

   /**
    * Default (empty) constructor
    */
   public SortFilterChain() {
   }

   /**
    * Parametrized constructor.
    *
    * @param field     name of the object property after which will be sorted
    * @param ascending if true then sort ascending otherwise descending
    */
   public SortFilterChain(String field, boolean ascending) {
      this.field = field;
      this.ascending = ascending;
   }

   /**
    * If you want to get more sorting objects, you can add it.
    * Next sorting priority is defined by given object
    * i.e. first sort by date then by name
    *
    * @param sortFilterChain next sort field
    * @return updated itself
    */
   public SortFilterChain append(SortFilterChain sortFilterChain) {
      next = sortFilterChain;
      return this;
   }

   /**
    * Returns field
    *
    * @return {@link #field} sorting field
    */
   public String getField() {
      return field;
   }

   /**
    * Sets field
    *
    * @param field sorting field
    */
   public void setField(String field) {
      this.field = field;
   }

   /**
    * Determines sorting order. If true order should be ascending, otherwise it should be descending
    *
    * @return {@link #ascending} if order should be ascending.
    */
   public boolean isAscending() {
      return ascending;
   }

   /**
    * Sets sorting order.
    *
    * @param ascending if true, then ascending order, otherwise descending order.
    */
   public void setAscending(boolean ascending) {
      this.ascending = ascending;
   }

   /**
    * Returns next SortFilter.
    *
    * @return {@link #next} SortFilterChain
    * @see #append(SortFilterChain)
    */
   public SortFilterChain getNext() {
      return next;
   }

   /**
    * Sets next SortFilterChain element.
    *
    * @param next SortFilterChain element
    * @see #append(SortFilterChain)
    */
   public void setNext(SortFilterChain next) {
      this.next = next;
   }

   /**
    * Overrides Object's toString().
    * toString() is a combination of {@link #field}, {@link #ascending} and {@link #next}
    *
    * @return toString representation of this object
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this)
            .append(FIELD_FIELD, field)
            .append(FIELD_ASCENDING, ascending)
            .append(FIELD_NEXT, next)
            .toString();
   }
}
