package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.IConnectionStationRelationDao;
import pl.ftt.domain.rel.ConnectionStationRelation;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("connectionStationRelationDao")
public class ConnectionStationRelationDaoImpl extends AbstractDaoImpl<ConnectionStationRelation> implements
        IConnectionStationRelationDao
{
}
