package pl.ftt.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Marek on 2015-11-12.
 */
@Entity
@Table(name = "\"station\"")
public class Station extends AbstractEntity
{
   public static final String FIELD_ACTIVE = "active";

   public static final String FIELD_NAME = "name";

   public static final String FIELD_GPS_LAT = "gpaLat";

   public static final String FIELD_GPS_LON = "gpsLon";

   public static final int FIELD_NAME_LENGTH = 128;

   @Column(nullable = false, name = "name", length = FIELD_NAME_LENGTH)
   @Length(max = FIELD_NAME_LENGTH)
   @NotBlank
   private String name;

   @Column(nullable = false, name = "active")
   private boolean active;

   @Column(nullable = true, name = "gps_lat")
   private Double gpsLat;

   @Column(nullable = true, name = "gps_lon")
   private Double gpsLon;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }

   public Double getGpsLat()
   {
      return gpsLat;
   }

   public void setGpsLat(Double gpsLat)
   {
      this.gpsLat = gpsLat;
   }

   public Double getGpsLon()
   {
      return gpsLon;
   }

   public void setGpsLon(Double gpsLon)
   {
      this.gpsLon = gpsLon;
   }
}
