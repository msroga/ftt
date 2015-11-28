package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.IScheduldeExceptionDao;
import pl.ftt.domain.ScheduldeException;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("scheduldeException")
public class ScheduldeExceptionDaoImpl extends AbstractDaoImpl<ScheduldeException> implements IScheduldeExceptionDao
{
}
