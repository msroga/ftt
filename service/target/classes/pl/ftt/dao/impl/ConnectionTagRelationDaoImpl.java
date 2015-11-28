package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.IConnectionTagRelationDao;
import pl.ftt.domain.rel.ConnectionTagRelation;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("connectionTagRelationDao")
public class ConnectionTagRelationDaoImpl extends AbstractDaoImpl<ConnectionTagRelation> implements
        IConnectionTagRelationDao
{
}
