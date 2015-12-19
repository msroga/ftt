package pl.ftt.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.IConnectionTagRelationDao;
import pl.ftt.dao.ITagDao;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Tag;
import pl.ftt.service.ITagService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
@Service("tagService")
public class TagServiceImpl extends AbstractServiceImpl<Tag> implements ITagService
{
   @Resource
   private ITagDao tagDao;

   @Resource
   private IConnectionTagRelationDao connectionTagRelationDao;

   @Override
   protected IAbstractDao<Tag> getDao()
   {
      return tagDao;
   }

   @Override
   @Transactional(readOnly = true)
   public List<Tag> find(Connection connection)
   {
      return connectionTagRelationDao.find(connection);
   }
}
