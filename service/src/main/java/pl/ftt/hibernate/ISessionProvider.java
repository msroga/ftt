package pl.ftt.hibernate;

import org.hibernate.Session;

/**
 * Session holder. Usually, without multi-tenancy, this class could be omitted. It was provided, just to make multi-tenancy
 * possible. Main aim is to keep current session
 *
 * @see Session
 */
public interface ISessionProvider
{
   Session getCurrentSession();
}
