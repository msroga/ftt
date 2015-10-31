package pl.myo.domain.filters;

import pl.myo.domain.OpinionTypeEnum;
import pl.myo.domain.User;

/**
 * Created by Marek on 2015-06-13.
 */
public class OpinionFilter implements IFilter
{
   public static final String FIELD_DOCTOR = "doctor";

   public static final String FIELD_TYPE = "type";

   private User doctor;

   private OpinionTypeEnum type;

   public User getDoctor()
   {
      return doctor;
   }

   public void setDoctor(User doctor)
   {
      this.doctor = doctor;
   }

   public OpinionTypeEnum getType()
   {
      return type;
   }

   public void setType(OpinionTypeEnum type)
   {
      this.type = type;
   }
}
