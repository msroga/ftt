package pl.ftt.dao;

import pl.ftt.domain.Connection;
import pl.ftt.domain.filters.ConnectionFilter;

/**
 * Created by Marek on 2015-11-12.
 */
public interface IConnectionDao extends IAbstractDao<Connection>
{
   void find(ConnectionFilter filter);
}
