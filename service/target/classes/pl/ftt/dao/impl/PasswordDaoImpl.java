package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.IPasswordDao;
import pl.ftt.domain.Password;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("passwordDao")
public class PasswordDaoImpl extends AbstractDaoImpl<Password> implements IPasswordDao
{
}
