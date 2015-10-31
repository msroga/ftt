package pl.myo.factory;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CacheProviderFactoryBean implements FactoryBean<ICacheProvider>, InitializingBean, DisposableBean
{
   private ICacheProvider cacheProvider;

   private Properties properties;

   private Class<? extends ICacheProvider> providerClass;

   @Override
   public void afterPropertiesSet() throws Exception
   {
      try
      {
         cacheProvider = providerClass.newInstance();
      }
      catch (Exception e)
      {
         throw new RuntimeException("unable to initialize cache provider from class " + providerClass, e);
      }

      Map<String, Object> map = new HashMap<String, Object>();
      String prefix = "cache.provider." + cacheProvider.getClass().getSimpleName() + ".";
      int len = prefix.length();
      Set<String> propertyNames = properties.stringPropertyNames();
      for (String properyName : propertyNames)
      {
         if (properyName.startsWith(prefix))
         {
            String name = properyName.substring(len);
            Object value = properties.getProperty(properyName);
            map.put(name, value);
         }
      }
      BeanUtils.populate(cacheProvider, map);
   }

   @Override
   public ICacheProvider getObject() throws Exception
   {
      return cacheProvider;
   }

   @Override
   public Class<?> getObjectType()
   {
      return ICacheProvider.class;
   }

   @Override
   public boolean isSingleton()
   {
      return true;
   }

   public Class<? extends ICacheProvider> getProviderClass()
   {
      return providerClass;
   }

   public void setProviderClass(Class<? extends ICacheProvider> providerClass)
   {
      this.providerClass = providerClass;
   }

   public Properties getProperties()
   {
      return properties;
   }

   public void setProperties(Properties properties)
   {
      this.properties = properties;
   }

   @Override
   public void destroy() throws Exception
   {
      if (cacheProvider instanceof DisposableBean)
      {
         ((DisposableBean)cacheProvider).destroy();
      }
   }
}
