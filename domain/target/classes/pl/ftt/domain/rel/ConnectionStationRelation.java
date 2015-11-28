package pl.ftt.domain.rel;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.ftt.domain.AbstractEntity;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-11-12.
 */
@Entity
@Table(name = "\"connection_station_relation\"")
public class ConnectionStationRelation extends AbstractEntity
{
   public static final String FIELD_CONNECTION = "connection";

   public static final String FIELD_STATION = "station";

   public static final String FIELD_INDEX = "index";

   public static final String FIELD_ARRIVAL_TIME = "arrivalTime";

   public static final String FIELD_DEPARTURE_TIME = "departureTime";

   @ManyToOne(optional = false)
   @JoinColumn(name = "connection_id", nullable = false)
   @NotNull
   private Connection connection;

   @ManyToOne(optional = false)
   @JoinColumn(name = "station_id", nullable = false)
   @NotNull
   private Station station;

   @Column(name = "index", nullable = false)
   private int index;

   @Column(name = "arrival_time", nullable = false)
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
   @NotNull
   private DateTime arrivalTime;

   @Column(name = "departure_time", nullable = false)
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
   @NotNull
   private DateTime departureTime;

   public Connection getConnection()
   {
      return connection;
   }

   public void setConnection(Connection connection)
   {
      this.connection = connection;
   }

   public Station getStation()
   {
      return station;
   }

   public void setStation(Station station)
   {
      this.station = station;
   }

   public int getIndex()
   {
      return index;
   }

   public void setIndex(int index)
   {
      this.index = index;
   }

   public DateTime getArrivalTime()
   {
      return arrivalTime;
   }

   public void setArrivalTime(DateTime arrivalTime)
   {
      this.arrivalTime = arrivalTime;
   }

   public DateTime getDepartureTime()
   {
      return departureTime;
   }

   public void setDepartureTime(DateTime departureTime)
   {
      this.departureTime = departureTime;
   }
}
