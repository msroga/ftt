package pl.myo.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import pl.myo.dao.IAbstractDao;
import pl.myo.domain.AbstractEntity;
import pl.myo.exception.DataNotFoundException;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.PagingRequest;
import pl.myo.domain.filters.SortFilterChain;
import pl.myo.service.IAbstractService;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marek on 2015-05-23.
 */
public abstract class AbstractServiceImpl<T extends AbstractEntity> implements IAbstractService<T>
{
   protected abstract IAbstractDao<T> getDao();

   @Override
   @Transactional(readOnly = true)
   public List<T> findBySearchParams(IFilter filter, SortFilterChain sortFilterChain, int offset, int limit)
   {
      return getDao().findBySearchParams(filter, sortFilterChain, offset, limit);
   }

   @Override
   @Transactional(readOnly = true)
   public List<T> findBySearchParams(IFilter filter, PagingRequest pagingRequest)
   {
      return getDao().findBySearchParams(filter, pagingRequest);
   }

   @Override
   @Transactional(readOnly = true)
   public long countBySearchParams(IFilter filter)
   {
      return getDao().countBySearchParams(filter);
   }

   @Transactional
   public T save(T obj)
   {
      return getDao().save(obj);
   }

   @Transactional(readOnly = true)
   public T merge(T entity)
   {
      return getDao().merge(entity);
   }

   @Transactional(readOnly = true)
   public void refresh(T entity)
   {
      getDao().refresh(entity);
   }

   @Override
   public void refresh(Collection<T> entities)
   {
      getDao().refresh(entities);
   }

   @Transactional
   public T update(T entity)
   {
      getDao().update(entity);
      return entity;
   }

   @Transactional
   public void delete(T entity)
   {
      getDao().delete(entity);
   }

   @Transactional
   public void deleteList(Collection<T> list)
   {
      if (!CollectionUtils.isEmpty(list))
      {
         for (T obj : list)
         {
            delete(obj);
         }
      }
   }

   @Transactional
   public void saveList(Collection<T> list)
   {
      if (!CollectionUtils.isEmpty(list))
      {
         getDao().saveList(list);
      }
   }

   public static final void assertEntityNotNull(AbstractEntity entity)
   {
      if (entity == null)
      {
         throw new DataNotFoundException("Entity not found");
      }
   }

   @Transactional(readOnly = true)
   public List<T> findAll()
   {
      return getDao().findAll();
   }

   @Transactional(readOnly = true)
   public long countAll()
   {
      return getDao().countAll();
   }


   @Transactional(readOnly = true)
   public T getById(Long id)
   {
      return getDao().getById(id);
   }

   @Override
   @Transactional(readOnly = true)
   public List<T> findByIds(Collection<Long> ids)
   {
      return getDao().findByIds(ids);
   }
}
