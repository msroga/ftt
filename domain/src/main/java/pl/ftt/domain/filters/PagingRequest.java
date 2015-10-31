package pl.ftt.domain.filters;

import java.io.Serializable;

/**
 * author: bartosz.forysiak@solsoft.pl
 * Date: 28.09.14
 */
public class PagingRequest implements Serializable
{
   public static int DEFAULT_RESULTS_PER_PAGE = 100;

   private int offset;

   private int limit;

   private SortFilterChain sortFilterChain;

   public PagingRequest()
   {
   }

   public PagingRequest(int offset, int limit, SortFilterChain sortFilterChain)
   {
      this.offset=offset;
      this.limit=limit;
      this.sortFilterChain = sortFilterChain;
   }

   public PagingRequest(int offset)
   {
      this(offset, DEFAULT_RESULTS_PER_PAGE, null);
   }

   public PagingRequest(int offset, int limit)
   {
      this(offset, limit, null);
   }

   public PagingRequest(int offset, SortFilterChain sortFilterChain)
   {
      this(offset, DEFAULT_RESULTS_PER_PAGE, sortFilterChain);
   }

   public int getOffset()
   {
      return offset;
   }

   public void setOffset(int offset)
   {
      this.offset = offset;
   }

   public int getLimit()
   {
      return limit;
   }

   public void setLimit(int limit)
   {
      this.limit = limit;
   }

   public SortFilterChain getSortFilterChain()
   {
      return sortFilterChain;
   }

   public void setSortFilterChain(SortFilterChain sortFilterChain)
   {
      this.sortFilterChain = sortFilterChain;
   }
}
