package pl.ftt.dao.impl;

import junit.framework.TestCase;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.ftt.domain.AbstractEntity;
import pl.ftt.hibernate.ISessionProvider;

import javax.annotation.Resource;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations =
        {
                "classpath:spring/test-dao-context.xml"
        })
@Ignore
public abstract class BaseDaoTest
{
   @Resource
   protected ISessionProvider sessionProvider;

   private static ValidatorFactory factory;

   @BeforeClass
   public static void setUpClass()
   {
      factory = Validation.buildDefaultValidatorFactory();
   }

   protected void persist(AbstractEntity... entries)
   {
      Session session = sessionProvider.getCurrentSession();
      for (AbstractEntity entry : entries)
      {
         factory.getValidator().validate(entry);
         session.saveOrUpdate(entry);
      }
   }

   protected void persist(List<? extends AbstractEntity> entries)
   {
      Session session = sessionProvider.getCurrentSession();
      for (Object entry : entries)
      {
         factory.getValidator().validate(entry);
         session.saveOrUpdate(entry);
      }
   }

   protected <S> void deepEquals(List<S> expected, List<S> result)
   {
      TestCase.assertNotNull(expected);
      TestCase.assertNotNull(result);
      TestCase.assertEquals(expected.size(), result.size());
      for (Object o : expected)
      {
         TestCase.assertTrue(result.contains(o));
      }
   }

   protected Criteria getCriteria(Class<?> clazz)
   {
      return sessionProvider.getCurrentSession().createCriteria(clazz);
   }

   @SuppressWarnings("unchecked")
   protected <T> T getById(Class<T> clazz, Long id)
   {
      return (T) sessionProvider.getCurrentSession().get(clazz, id);
   }
}