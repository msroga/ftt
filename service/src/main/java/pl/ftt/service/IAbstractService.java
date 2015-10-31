package pl.ftt.service;

import pl.ftt.domain.AbstractEntity;
import pl.ftt.domain.filters.IFilter;
import pl.ftt.domain.filters.PagingRequest;
import pl.ftt.domain.filters.SortFilterChain;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marek on 2015-05-23.
 */
public interface IAbstractService<T extends AbstractEntity>
{
   T save(T entity);

   void saveList(Collection<T> entities);

   void delete(T entity);

   void deleteList(Collection<T> entities);

   T getById(Long id);

   List<T> findByIds(Collection<Long> ids);

   List<T> findAll();

   long countAll();

   T merge(T entity);

   void refresh(T entity);

   void refresh(Collection<T> entities);

   T update(T entity);

   long countBySearchParams(IFilter filter);

   List<T> findBySearchParams(IFilter filter, PagingRequest pagingRequest);

   List<T> findBySearchParams(IFilter filter, SortFilterChain sortFilterChain, int offset, int limit);
}
