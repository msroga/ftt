package pl.ftt.domain.filters;

import org.joda.time.LocalTime;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;

import java.util.List;

/**
 * Created by Marek on 2015-11-20.
 */
public class ConnectionFilter implements IFilter
{
   public static final String FILTER_STATION_FROM = "stationFrom";

   public static final String FILTER_STATION_TO = "stationTo";

   public static final String FILTER_TAGS = "tags";

   public static final String FILTER_TIME = "time";

   private Station stationFrom;

   private Station stationTo;

   private List<Tag> tags;

   private LocalTime time;

   public Station getStationFrom()
   {
      return stationFrom;
   }

   public void setStationFrom(Station stationFrom)
   {
      this.stationFrom = stationFrom;
   }

   public Station getStationTo()
   {
      return stationTo;
   }

   public void setStationTo(Station stationTo)
   {
      this.stationTo = stationTo;
   }

   public List<Tag> getTags()
   {
      return tags;
   }

   public void setTags(List<Tag> tags)
   {
      this.tags = tags;
   }

   public LocalTime getTime()
   {
      return time;
   }

   public void setTime(LocalTime time)
   {
      this.time = time;
   }
}
