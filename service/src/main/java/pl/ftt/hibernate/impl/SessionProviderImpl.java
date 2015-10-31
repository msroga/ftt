package pl.ftt.hibernate.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.ftt.hibernate.ISessionProvider;

/**
 * Data holder to session factory used to connection with database.
 *
 * @see SessionFactory
 * @see ISessionProvider
 */
public class SessionProviderImpl implements ISessionProvider
{
   /**
    * Session factory - should be injected by spring
    */
   private SessionFactory sessionFactory;

   /**
    * Returns current session from the sessionFactory
    *
    * @return current session
    * @see SessionFactory
    */
   @Override
   public Session getCurrentSession() {
      return sessionFactory.getCurrentSession();
   }

   /**
    * Returns session factory. See {@link #sessionFactory}
    *
    * @return
    */
   public SessionFactory getSessionFactory() {
      return sessionFactory;
   }

   /**
    * Sets session factory. See {@link #sessionFactory}
    *
    * @param sessionFactory session factory to set
    */
   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }
}
