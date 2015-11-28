package pl.ftt.service.impl;

import org.springframework.stereotype.Service;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.IConnectionDao;
import pl.ftt.domain.Connection;
import pl.ftt.service.IConnectionService;

import javax.annotation.Resource;

/**
 * Created by Marek on 2015-11-12.
 */
@Service("connectionService")
public class ConnectionServiceImpl extends AbstractServiceImpl<Connection> implements IConnectionService
{
   @Resource
   private IConnectionDao connectionDao;

   @Override
   protected IAbstractDao<Connection> getDao()
   {
      return connectionDao;
   }
}
