package pl.ftt.domain.filters;

/**
 * Created by Marek on 2015-11-20.
 */
public class ConnectionFilter implements IFilter
{
   public static final String FILTER_STATION_FROM = "stationFrom";

   public static final String FILTER_STATION_TO = "stationTo";

   private String stationFrom;

   private String stationTo;

   public String getStationFrom()
   {
      return stationFrom;
   }

   public void setStationFrom(String stationFrom)
   {
      this.stationFrom = stationFrom;
   }

   public String getStationTo()
   {
      return stationTo;
   }

   public void setStationTo(String stationTo)
   {
      this.stationTo = stationTo;
   }
}
