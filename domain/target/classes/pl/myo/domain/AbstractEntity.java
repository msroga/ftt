package pl.myo.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@MappedSuperclass
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractEntity implements Serializable
{
   public static final String FIELD_ID = "id";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @XmlTransient
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

   @Override
   public boolean equals(Object obj)
   {
      if (obj != null && obj.getClass().equals(getClass()))
      {
         if (getDefaultId() != null && ((AbstractEntity) obj).getDefaultId() != null
                 && getDefaultId().equals(((AbstractEntity) obj).getDefaultId()))
         {
            return true;
         }
      }
      return super.equals(obj);
   }

   /**
    * Entity to String conversion
    *
    * @return id of Entity in square brackets
    */
   @Override
   public String toString()
   {
      if (getDefaultId() == null)
      {
         return "[] ";
      }
      return "[" + getDefaultId() + "] ";
   }

   public String getDefaultId()
   {
      return id != null ? id.toString() : null;
   }
}
