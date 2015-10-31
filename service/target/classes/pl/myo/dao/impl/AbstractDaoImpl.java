package pl.myo.dao.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.sql.JoinType;
import pl.myo.dao.IAbstractDao;
import pl.myo.domain.AbstractEntity;
import pl.myo.domain.User;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.PagingRequest;
import pl.myo.domain.filters.SortFilterChain;
import pl.myo.domain.rel.PatientDoctorRelation;
import pl.myo.hibernate.ISessionProvider;
import pl.myo.utils.ReflectionUtils;

import javax.annotation.Resource;
import javax.persistence.Embeddable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class AbstractDaoImpl<T extends AbstractEntity> implements IAbstractDao<T>
{
   private static final String ALIAS = "alias";

   private static final String[] ORDER_FIELDS = new String[]
           {
                   AbstractEntity.FIELD_ID
           };

   /**
    * Session provider. See {@link ISessionProvider}
    */
   @Resource
   protected ISessionProvider sessionProvider;

   /**
    * Value of <T> parameter
    */
   private final Class<?> aClass;

   /**
    * Default constructor sets {@link #aClass} via Reflection.
    */
   public AbstractDaoImpl()
   {
      ParameterizedType daoType = (ParameterizedType) getClass().getGenericSuperclass();
      aClass = (Class<T>) daoType.getActualTypeArguments()[0];
   }

   @SuppressWarnings("unchecked")
   public T merge(T entity)
   {
      return (T) getCurrentSession().merge(entity);
   }

   @Override
   public T save(T entity)
   {
      getCurrentSession().persist(entity);
      return entity;
   }

   @Override
   public List<T> saveList(List<T> entities)
   {
      for (T entity : entities)
      {
         getCurrentSession().saveOrUpdate(entity);
      }
      return entities;
   }

   public T refresh(T entity)
   {
      getCurrentSession().refresh(entity);
      return entity;
   }

   @Override
   public void refresh(Collection<T> entities)
   {
      if (!CollectionUtils.isEmpty(entities))
      {
         for (T entity : entities)
         {
            refresh(entity);
         }
      }
   }

   @Override
   public T update(T entity)
   {
      getCurrentSession().update(entity);
      return entity;
   }

   public void saveList(Collection<T> entities)
   {
      for (T entity : entities)
      {
         getCurrentSession().saveOrUpdate(entity);
      }
   }

   @Override
   public void delete(T entity)
   {
      getCurrentSession().delete(entity);
   }

   @Override
   public void deleteList(List<T> entities)
   {
      Session session = getCurrentSession();
      for (T entity : entities)
      {
         session.delete(entity);
      }
   }

   @Override
   public void deleteList(Collection<T> entities)
   {
      Session session = getCurrentSession();
      for (T entity : entities)
      {
         session.delete(entity);
      }
   }

   @Override
   public void flush()
   {
      getCurrentSession().flush();
   }

   @Override
   public T getById(Long id)
   {
      return (T) getCurrentSession().get(aClass, id);
   }

   @Override
   public List<T> findByIds(Collection<Long> ids)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.in(AbstractEntity.FIELD_ID, ids));
      return criteria.list();
   }

   protected final Criteria createCriteria()
   {
      return getCurrentSession().createCriteria(aClass);
   }

   protected final Criteria createCriteria(String alias)
   {
      return getCurrentSession().createCriteria(aClass, alias);
   }

   protected final Criteria createCriteria(Class<?> clazz)
   {
      return getCurrentSession().createCriteria(clazz);
   }

   @Override
   public List<T> findAll()
   {
      Criteria criteria = createCriteria();
      return criteria.list();
   }

   public long countAll()
   {
      Criteria criteria = createCriteria();
      criteria.setProjection(Projections.count(AbstractEntity.FIELD_ID));
      return (Long) criteria.uniqueResult();
   }

   protected String[] getOrderFields()
   {
      return ORDER_FIELDS;
   }

   protected boolean isOrderAsc()
   {
      return true;
   }

   protected String[] getAllowedSortFields()
   {
      return null;
   }

   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      return createCriteria();
   }

   protected void applyPaging(Criteria criteria, int offset, int limit)
   {
      criteria.setMaxResults(limit);
      criteria.setFirstResult(offset);
   }

   @Override
   public long countBySearchParams(IFilter filter)
   {
      Criteria criteria = createCriteriaFromSearchParams(filter);
      criteria.setProjection(Projections.rowCount());
      return (Long) criteria.uniqueResult();
   }

   @Override
   public List<T> findBySearchParams(IFilter filter, PagingRequest pagingRequest)
   {
      Criteria criteria = createCriteriaFromSearchParams(filter);
      addOrder(criteria, pagingRequest.getSortFilterChain());
      applyPaging(criteria, pagingRequest.getOffset(), pagingRequest.getLimit());
      return criteria.list();
   }

   @Override
   public List<T> findBySearchParams(IFilter filter, SortFilterChain sortFilterChain, int offset, int limit)
   {
      Criteria criteria = createCriteriaFromSearchParams(filter);
      addOrder(criteria, sortFilterChain);
      applyPaging(criteria, offset, limit);
      return criteria.list();
   }

   public boolean isSortFieldAllowed(SortFilterChain sortFilterChain)
   {
      if (!StringUtils.isBlank(sortFilterChain.getField()))
      {
         String[] sortFields = getAllowedSortFields();
         if (sortFields == null)
         {
            return true;
         }
         else
         {
            for (String sortField : sortFields)
            {
               if (sortFilterChain.getField().equals(sortField))
               {
                  return true;
               }
            }
         }
      }
      return false;
   }

   protected void addOrder(Criteria criteria, SortFilterChain sortFilterChain)
   {
      SortFilterChain clone = SerializationUtils.clone(sortFilterChain);
      if (clone != null)
      {
         while (clone != null)
         {
            if (isSortFieldAllowed(clone))
            {
               prepareAliases(criteria, clone);
               criteria.addOrder(clone.isAscending() ? Order.asc(clone.getField()) : Order.desc(clone.getField()));
            }
            clone = clone.getNext();
         }
      }
      else
      {
         if (getOrderFields() != null && getOrderFields().length > 0)
         {
            for (String sortField : getOrderFields())
            {
               criteria.addOrder(isOrderAsc() ? Order.asc(sortField) : Order.desc(sortField));
            }
         }
      }
   }

   private boolean isEmbedded(Class<?> clazz)
   {
      return clazz.isAnnotationPresent(Embeddable.class);
   }

   /**
    * Returns existing alias for subcriteria, by given criteria and alias.
    * In case of subcriteria's not found - null is being returned.
    *
    * @param criteria criteria
    * @param alias    alias to be found
    * @return subcriteria's alias
    */
   private String getExistingAlias(Criteria criteria, String alias)
   {
      CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
      Iterator<CriteriaImpl.Subcriteria> iterator = criteriaImpl.iterateSubcriteria();
      while (iterator.hasNext())
      {
         CriteriaImpl.Subcriteria subcriteria = iterator.next();
         if (subcriteria.getPath().equals(alias))
         {
            return subcriteria.getAlias();
         }
      }
      return null;
   }

   protected void prepareAliases(Criteria criteria, SortFilterChain sortFilterChain)
   {
      try
      {
         StringTokenizer tokenizer = new StringTokenizer(sortFilterChain.getField(), ".");
         if (tokenizer.countTokens() >= 2)
         {
            Random random = new Random();
            String alias = tokenizer.nextToken();
            String field = tokenizer.nextToken();
            String full = alias;
            Class<?> clazz = ReflectionUtils.getNestedField(aClass, alias).getType();
            full = handleCreateAlias(criteria, random, alias, full, clazz);
            while (tokenizer.hasMoreTokens())
            {
               alias = field;
               field = tokenizer.nextToken();
               clazz = ReflectionUtils.getNestedField(clazz, alias).getType();
               full += "." + alias;
               full = handleCreateAlias(criteria, random, alias, full, clazz);
            }
            sortFilterChain.setField(full + "." + field);
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException("Prepare aliases failed!", e);
      }
   }

   private String handleCreateAlias(Criteria criteria, Random random, String alias, String full, Class<?> clazz)
   {
      if (!isEmbedded(clazz))
      {
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append(ALIAS);
         stringBuilder.append(Math.abs(random.nextInt()));
         String randomAlias = stringBuilder.toString();
         String existing = getExistingAlias(criteria, full);
         if (existing != null)
         {
            full = existing;
         }
         else
         {
            criteria.createAlias(full, randomAlias, JoinType.LEFT_OUTER_JOIN);
            full = randomAlias;
         }
      }
      return full;
   }

   /**
    * Returns current session from {@link ISessionProvider}
    *
    * @return current session
    * @see Session
    */
   protected Session getCurrentSession()
   {
      return sessionProvider.getCurrentSession();
   }

   @Override
   public void postgresDisableConstraints()
   {
      Session session = getCurrentSession();
      Query query = session.createSQLQuery("SET CONSTRAINTS ALL DEFERRED;");
      query.executeUpdate();
      session.flush();
   }

   @Override
   public void postgresEnableConstraints()
   {
      Session session = getCurrentSession();
      Query query = session.createSQLQuery("SET CONSTRAINTS ALL IMMEDIATE;");
      query.executeUpdate();
      session.flush();
   }

   /**
    * @param propertyName fieldName, i.e.: email
    * @param value        value of propertyName, i.e.: krzysztof.ryk@solsoft.pl
    * @return entity by given criterion
    */
   @Override
   public T getBy(String propertyName, Object value)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(propertyName, value));
      return (T) criteria.uniqueResult();
   }

   /**
    * @param propertyName fieldName, i.e.: prefix
    * @param value        value of field, i.e.: camel
    * @return list of objects matching to given criteria
    */
   @Override
   public List<T> findBy(String propertyName, Object value)
   {
      Criteria criteria = createCriteria();
      criteria.add(Restrictions.eq(propertyName, value));
      return criteria.list();
   }
}
