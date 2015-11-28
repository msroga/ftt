package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.IConnectionDao;
import pl.ftt.domain.Connection;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("connectionDao")
public class ConnectionDaoImpl extends AbstractDaoImpl<Connection> implements IConnectionDao
{
}
