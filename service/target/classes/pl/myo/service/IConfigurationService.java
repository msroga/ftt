package pl.myo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Marek on 2015-05-16.
 */
public interface IConfigurationService
{
   Object getConfiguration(Class<?> target, String key);

   String getConfiguration(String key);

   Map<String, String> getConfigurationMap(Collection<String> keys);

   List<String> findAllKeys();

   Map<String, String> findAllConfiguration();
}
