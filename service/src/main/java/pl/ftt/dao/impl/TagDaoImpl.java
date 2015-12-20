package pl.ftt.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.ftt.dao.ITagDao;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.IFilter;
import pl.ftt.domain.filters.TagFilter;

/**
 * Created by Marek on 2015-11-12.
 */
@Repository("tagDao")
public class TagDaoImpl extends AbstractDaoImpl<Tag> implements ITagDao
{
   @Override
   public Criteria createCriteriaFromSearchParams(IFilter filter)
   {
      Criteria criteria =  super.createCriteriaFromSearchParams(filter);
      if (TagFilter.class.isAssignableFrom(filter.getClass()))
      {
         TagFilter tagFilter = (TagFilter) filter;

         if (StringUtils.isNotBlank(tagFilter.getName()))
         {
            criteria.add(Restrictions.ilike(Tag.FIELD_NAME, tagFilter.getName(), MatchMode.START));
         }

         if (tagFilter.getActive() != null)
         {
            criteria.add(Restrictions.eq(Tag.FIELD_ACTIVE, tagFilter.getActive()));
         }
      }

      return criteria;
   }
}
