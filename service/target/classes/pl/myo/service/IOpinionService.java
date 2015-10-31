package pl.myo.service;

import org.springframework.transaction.annotation.Transactional;
import pl.myo.domain.Opinion;
import pl.myo.domain.User;

/**
 * Created by Marek on 2015-06-04.
 */
public interface IOpinionService extends IAbstractService<Opinion>
{
   boolean isOpinioned(User author, User doctor);
}
