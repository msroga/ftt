package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.IUserDao;
import pl.ftt.domain.User;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements IUserDao
{
}
