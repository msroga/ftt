package pl.ftt.service.impl;

import pl.ftt.service.IConfigurationService;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.*;

/**
 * Created by Marek on 2015-05-16.
 */
public class ConfigurationServiceImpl implements IConfigurationService
{
   protected Properties configurationProperties;

   @Override
   public Object getConfiguration(Class<?> target, String key)
   {
      PropertyEditor propertyEditor = PropertyEditorManager.findEditor(target);
      propertyEditor.setAsText(getConfiguration(key));
      return propertyEditor.getValue();
   }

   @Override
   public String getConfiguration(String key)
   {
      return configurationProperties.getProperty(key);
   }

   @Override
   public Map<String, String> getConfigurationMap(Collection<String> keys)
   {
      Map<String, String> result = new HashMap<String, String>();
      if (keys == null)
      {
         return result;
      }
      for (String key : keys)
      {
         result.put(key, getConfiguration(key));
      }
      return result;
   }

   @Override
   public List<String> findAllKeys()
   {
      List<String> keys = new ArrayList<String>();
      Enumeration enumeration = configurationProperties.keys();
      for (; enumeration.hasMoreElements(); )
      {
         keys.add(enumeration.nextElement().toString());
      }
      return keys;
   }

   @Override
   public Map<String, String> findAllConfiguration()
   {
      List<String> keys = findAllKeys();

      return getConfigurationMap(keys);
   }

   public Properties getConfigurationProperties()
   {
      return configurationProperties;
   }

   public void setConfigurationProperties(Properties configurationProperties)
   {
      this.configurationProperties = configurationProperties;
   }
}
