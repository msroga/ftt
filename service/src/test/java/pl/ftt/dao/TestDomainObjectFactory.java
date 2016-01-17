package pl.ftt.dao;

import pl.ftt.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Marek on 2016-01-17.
 */
public class TestDomainObjectFactory
{
   private static final Random RANDOM = new Random();

   public static Station getStation()
   {
      Station station = new Station();
      station.setName("station" + RANDOM.nextInt());
      station.setActive(true);
      return station;
   }

   public static List<Station> getStations(int quantity)
   {
      List<Station> stations = new ArrayList<>();

      for (int i = 0; i < quantity ; i++)
      {
         stations.add(getStation());
      }
      return stations;
   }
}
