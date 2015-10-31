package pl.myo.dao.impl;

import org.springframework.stereotype.Repository;
import pl.myo.dao.ITagDao;
import pl.myo.domain.Tag;

/**
 * Created by Marek on 2015-05-23.
 */
@Repository("tagDao")
public class TagDaoImpl extends AbstractDaoImpl<Tag> implements ITagDao
{
}
