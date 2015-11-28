package pl.ftt.service.impl;

import org.springframework.stereotype.Service;
import pl.ftt.dao.IAbstractDao;
import pl.ftt.dao.ITagDao;
import pl.ftt.domain.Tag;
import pl.ftt.service.ITagService;

import javax.annotation.Resource;

/**
 * Created by Marek on 2015-11-12.
 */
@Service("tagService")
public class TagServiceImpl extends AbstractServiceImpl<Tag> implements ITagService
{
   @Resource
   private ITagDao tagDao;

   @Override
   protected IAbstractDao<Tag> getDao()
   {
      return tagDao;
   }
}
