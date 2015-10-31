package pl.myo.dao;

import pl.myo.dao.impl.AbstractDaoImpl;
import pl.myo.domain.Opinion;
import pl.myo.domain.User;

import java.util.List;

/**
 * Created by Marek on 2015-06-04.
 */
public interface IOpinionDao extends IAbstractDao<Opinion>
{
   long count(User author, User doctor);
}
