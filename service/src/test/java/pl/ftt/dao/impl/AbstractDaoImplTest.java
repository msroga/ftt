package pl.ftt.dao.impl;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.domain.AbstractEntity;

import java.util.List;
import java.util.Random;

@Ignore
public abstract class AbstractDaoImplTest<T extends AbstractEntity> extends BaseDaoTest
{
   protected static final Random RANDOM = new Random();

   protected abstract IAbstractDao<T> getDao();

   protected abstract T getEntity();

   protected abstract List<T> getEntities();

   @Test
   public void testGetByIdUsingCriteria()
   {
      List<T> entities = getEntities();
      persist(entities);
      int randomIndex = RANDOM.nextInt(entities.size());
      T entity = entities.get(randomIndex);

      T result = getDao().getById(entity.getId());

      TestCase.assertNotNull(result);
      TestCase.assertEquals(entity, result);
   }

   @Test
   public void testSave()
   {
      T expected = getEntity();
      getDao().save(expected);

      AbstractEntity result = getDao().getById(expected.getId());

      TestCase.assertNotNull(result.getId());
      TestCase.assertEquals(expected, result);
   }

   @Test
   public void testGetById()
   {
      T expected = getEntity();
      persist(expected);

      T result = getDao().getById(expected.getId());

      TestCase.assertEquals(expected, result);
   }

   @Test
   public void testDelete()
   {
      T entity = getEntity();
      persist(entity);

      T result = getDao().getById(entity.getId());
      TestCase.assertNotNull(result);

      getDao().delete(entity);

      result = getDao().getById(entity.getId());
      TestCase.assertNull(result);
   }

   @SuppressWarnings("unchecked")
   protected <S> List<S> getAll(Class<S> clazz)
   {
      return getCriteria(clazz).list();
   }
}

