package pl.myo.factory;

import java.util.Date;

/**
 * Interface for all object which caching information.
 */
public interface ICacheProvider
{

   /**
    * Cache <code>value</code> under key <code>key</code>
    *
    * @param key   key to <code>value</code>
    * @param value value to cache
    */
   public void put(String key, Object value);

   /**
    * Cache <code>value</code> under key <code>key</code>.
    * Cached information will expire for <code>expiry</code> seconds.
    *
    * @param key    key to <code>value</code>
    * @param value  value to cache
    * @param expiry time to expire information in seconds
    */
   public void put(String key, Object value, int expiry);

   /**
    * Cache <code>value</code> under key <code>key</code>.
    * Cached information will expire on date <code>expiry</code>
    *
    * @param key    key to <code>value</code>
    * @param value  value to cache
    * @param expiry date to expire cached information
    */
   public void put(String key, Object value, Date expiry);

   /**
    * Returns object from cache by <code>key</code>.
    *
    * @param key key to get object
    * @return cached object
    */
   public Object get(String key);

   /**
    * Remove object from cache by <code>key</code>.
    *
    * @param key key to remove object
    * @return true/false depending on whether the object was removed
    */
   public boolean remove(String key);

   /**
    * Checks if the cache contains the <code>key</code>.
    * If contains returns true, otherwise false.
    *
    * @param key key to check
    * @return true/false
    */
   public boolean contains(String key);

   /**
    * Returns cache status.
    * If cache is active, returns true, otherwise false.
    *
    * @return true/false
    */
   public boolean enabled();

   /**
    * Clear all cached information.
    */
   public void clear();
}
