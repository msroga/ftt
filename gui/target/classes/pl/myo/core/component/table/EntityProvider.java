package pl.myo.core.component.table;

import org.apache.wicket.model.IModel;
import pl.myo.core.models.EntityModel;
import pl.myo.domain.AbstractEntity;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.SortFilterChain;
import pl.myo.service.IAbstractService;

import java.util.List;

public class EntityProvider<T extends AbstractEntity> extends AbstractProvider<T>
{
   private static final long serialVersionUID = 3656923947176480733L;

   protected final IAbstractService<T> service;

   public EntityProvider(IAbstractService<T> service, OpenSearchDescription<T> osd)
   {
      super(osd);
      this.service = service;
   }

   @Override
   public long size(IFilter filter)
   {
      return service.countBySearchParams(filter);
   }

   @Override
   public IModel<T> model(T object)
   {
      return new EntityModel<T>(object, service);
   }

   @Override
   public List<T> find(IFilter filter, SortFilterChain sortFilterChain, int first, int count)
   {
      return service.findBySearchParams(osd.getFilter(), sortFilterChain, first, count);
   }
}
