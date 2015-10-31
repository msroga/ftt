package pl.ftt.factory;

//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;

/**
 * Class to hold cache using Google guava framework.
 * Object's variables:
 * <ul>
 *     <li>
 *         {@link #maxCacheSize} max cache size
 *     </li>
 *     <li>
 *         {@link #expireAfterHours} expire cache time in hours
 *     </li>
 *     <li>
 *         {@link #CACHE} map to hold cache
 *     </li>
 * </ul>
 *
 * User: krzysztof.ryk@solsoft.pl
 * Date: 9/30/13 2:23 PM
 */
public class GuavaCacheProvider
{//implements ICacheProvider {

//    /**
//     * Object's max cache size.
//     */
//   private int maxCacheSize = 1000;
//
//    /**
//     * Object's expire time cache in hours.
//     */
//   private long expireAfterHours = 8;
//
//   /**
//    * Map to hold cache.
//    */
//   private Cache<String, Object> CACHE = CacheBuilder.newBuilder()
//         .expireAfterAccess(expireAfterHours, TimeUnit.HOURS).maximumSize(maxCacheSize).build();
//
//   /**
//    * @see ICacheProvider#put(String, Object)
//    */
//   @Override
//   public void put(String key, Object value) {
//      CACHE.put(key, value);
//   }
//
//   /**
//    * Method does not use expiry <code>parameter</code>.
//    *
//    * @param key    key to <code>value</code>
//    * @param value  value to cache
//    * @param expiry time to expire information in seconds
//    * @see ICacheProvider#put(String, Object, int)
//    */
//   @Override
//   public void put(String key, Object value, int expiry) {
//      CACHE.put(key, value);
//   }
//
//   /**
//    * Method does not use expiry <code>parameter</code>.
//    *
//    * @param key    key to <code>value</code>
//    * @param value  value to cache
//    * @param expiry date to expire cached information
//    * @see ICacheProvider#put(String, Object, java.util.Date)
//    */
//   @Override
//   public void put(String key, Object value, Date expiry) {
//      CACHE.put(key, value);
//   }
//
//   /**
//    * @see ICacheProvider#get(String)
//    */
//   @Override
//   public Object get(String key) {
//      return CACHE.getIfPresent(key);
//   }
//
//   /**
//    * @see ICacheProvider#remove(String)
//    */
//   @Override
//   public boolean remove(String key) {
//      CACHE.invalidate(key);
//      return true;
//   }
//
//   /**
//    * @see ICacheProvider#contains(String)
//    */
//   @Override
//   public boolean contains(String key) {
//      return CACHE.asMap().containsKey(key);
//   }
//
//   /**
//    * This cache can be always true.
//    *
//    * @return true
//    */
//   @Override
//   public boolean enabled() {
//      return true;
//   }
//
//   /**
//    * @see pl.solsoft.common.cache.api.ICacheProvider#clear()
//    */
//   @Override
//   public void clear() {
//      CACHE.invalidateAll();
//   }
//
//    /**
//     * Sets object's {@link #maxCacheSize}
//     * @param maxCacheSize max cache size to set
//     */
//   public void setMaxCacheSize(int maxCacheSize) {
//      this.maxCacheSize = maxCacheSize;
//   }
//
//    /**
//     * Sets object's {@link #expireAfterHours}
//     * @param expireAfterHours expire to set
//     */
//   public void setExpireAfterHours(long expireAfterHours) {
//      this.expireAfterHours = expireAfterHours;
//   }
}
