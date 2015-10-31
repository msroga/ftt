package pl.myo.dao;

import pl.myo.domain.AbstractEntity;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.PagingRequest;
import pl.myo.domain.filters.SortFilterChain;

import java.util.Collection;
import java.util.List;

/**
 * Created by Marek on 2015-05-23.
 */
public interface IAbstractDao<T extends AbstractEntity>
{
   /**
    * Save object <code>entity</code>.
    *
    * @param entity object to save
    * @return saved object
    */
   T save(T entity);

   /**
    * Saveli list of objects with interface Collection<T>
    *
    * @param entities list to save
    */
   void saveList(Collection<T> entities);

   /**
    * Save list of objects <code>entities</code>.
    *
    * @param entities list to save
    * @return saved list
    */
   List<T> saveList(List<T> entities);

   /**
    * Delete object <code>entity</code>.
    *
    * @param entity object to delete
    */
   void delete(T entity);

   /**
    * Delete list of entities.
    *
    * @param entities list to delete
    */
   void deleteList(List<T> entities);

   /**
    * Delete collection of entities.
    *
    * @param entities list to delete
    */
   void deleteList(Collection<T> entities);

   /**
    * Return object by <code>id</code>.
    *
    * @param id an identifier
    * @return a persistent instance or null if object does not exist
    */
   T getById(Long id);

   List<T> findByIds(Collection<Long> ids);

   /**
    * Return all objects in list.
    *
    * @return list of objects
    */
   List<T> findAll();

   /**
    * Return number of all saved objects.
    *
    * @return number of objects
    */
   long countAll();

   T merge(T entity);

   T refresh(T entity);

   void refresh(Collection<T> entities);

   /**
    * Update all fields of object.
    *
    * @param entity object
    * @return updated object
    */
   T update(T entity);

   /**
    * Return number of objects defined by <code>filter</code>.</br>
    *
    * @param filter defined filter
    * @return number of object
    */
   long countBySearchParams(IFilter filter);

   List<T> findBySearchParams(IFilter filter, PagingRequest pagingRequest);

   /**
    * Return sorted list of objects defined by params.
    *
    * @param filter          defined filter
    * @param sortFilterChain sort filter
    * @param offset          from which one object start
    * @param limit           how many objects return
    * @return list of objects
    */
   List<T> findBySearchParams(IFilter filter, SortFilterChain sortFilterChain, int offset, int limit);

   /**
    * The flush mode determines the points at which the session is flushed.</br>
    * <i>Flushing</i> is the process of synchronizing the underlying persistent.</br>
    * store with persistable state held in memory.</br>
    */
   void flush();

   /**
    * Sets the behavior of constraint checking within the current transaction until commit.
    */
   void postgresDisableConstraints();

   /**
    * Sets the behavior of constraint checking within the current transaction at the end of each statement.
    */
   void postgresEnableConstraints();

   /**
    * Generic method, which allows retrieving object by its property value. It will work only, if property is a part of
    * considered object. For instance, method can be used to retrieve user by email.
    * <p/>
    * NOTE: if you want to get a list of objects, please see at {@link #findBy(String, Object)}
    *
    * @param field fieldName, i.e.: email
    * @param value value of field, i.e.: krzysztof.ryk@solsoft.pl
    * @return object matching to given criteria
    */
   T getBy(String field, Object value);

   /**
    * Generic method, which allows retrieving objects by their property value. It will work only, if property is a part of
    * considered objects. For instance, method can be used to retrieve system parameters by prefix (exact match).
    *
    * @param propertyName fieldName, i.e.: prefix
    * @param value        value of field, i.e.: camel
    * @return
    */
   List<T> findBy(String propertyName, Object value);
}
