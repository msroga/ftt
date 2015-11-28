package pl.ftt.dao.impl;

import org.springframework.stereotype.Repository;
import pl.ftt.dao.ITagDao;
import pl.ftt.domain.Tag;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("tagDao")
public class TagDaoImpl extends AbstractDaoImpl<Tag> implements ITagDao
{
}
