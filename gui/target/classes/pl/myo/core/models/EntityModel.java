package pl.myo.core.models;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import pl.myo.domain.AbstractEntity;
import pl.myo.service.IAbstractService;

public class EntityModel<T extends AbstractEntity> extends LoadableDetachableModel<T>
{
   private static final long serialVersionUID = 7371109077016817589L;

   private final IAbstractService<T> service;

   private Long id;

   private Class<?> clazz;

   public EntityModel(T entity, IAbstractService<T> service)
   {
      super(entity);
      this.service = service;
      this.id = entity != null ? entity.getId() : null;
      clazz = entity != null ? entity.getClass() : null;
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (obj == this)
      {
         return true;
      }
      else if (obj == null)
      {
         return false;
      }
      else if (obj instanceof EntityModel)
      {
         EntityModel other = (EntityModel) obj;
         if (clazz != null && other.clazz != null)
         {
            return clazz.equals(other.clazz) && id != null ? id.equals(other.getId()) : other.getId() == null;
         }
      }
      else if (obj instanceof Model<?>)
      {
         Model<?> other = (Model<?>) obj;
         Object object = other.getObject();
         if (object != null && object.getClass().equals(clazz))
         {
            AbstractEntity abstractEntity = (AbstractEntity) object;
            return id != null ? id.equals(abstractEntity.getId()) : abstractEntity.getId() == null;
         }
      }
      return false;
   }

   @Override
   protected T load()
   {
      if (id != null)
      {
         return service.getById(id);
      }
      return null;
   }

   public Long getId()
   {
      return id;
   }

   @Override
   public String toString()
   {
      return id != null ? id.toString() : null;
   }

   @Override
   public void setObject(T entity)
   {
      super.setObject(entity);
      id = entity != null ? entity.getId() : null;
      clazz = entity != null ? entity.getClass() : null;
   }
}
