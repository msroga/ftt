package pl.ftt.dbcp;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * BasicDataSource implementation - main aim is to prevent memory leaks, caused by not de-registering driver in
 * {@link BasicDataSource}
 *
 * @see BasicDataSource
 */
public class BasicDataSourceImpl extends BasicDataSource
{
   /**
    * Overrides {@link org.apache.commons.dbcp.BasicDataSource#close()}. De-registers jdbc driver and prevents memory
    * leaks.
    *
    * @throws java.sql.SQLException
    * @see BasicDataSource#close()
    */
   @Override
   public synchronized void close() throws SQLException
   {
      DriverManager.deregisterDriver(DriverManager.getDriver(url));
      super.close();
   }

   @Override
   public Logger getParentLogger() throws SQLFeatureNotSupportedException
   {
      return null;
   }
}
