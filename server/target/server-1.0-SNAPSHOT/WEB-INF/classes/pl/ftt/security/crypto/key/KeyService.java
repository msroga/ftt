package pl.ftt.security.crypto.key;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import pl.ftt.security.crypto.ICryptoConstants;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * author: rafal.glowacz@solsoft.pl
 * created: 2011-01-05
 */
@Component(KeyService.BEAN_NAME)
public class KeyService extends AbstractKeyService implements ApplicationContextAware
{
   public static final String BEAN_NAME = "keyService";

   private Map<Byte, Key> keys = new HashMap<Byte, Key>();

   private Map<Byte, String> rawkeys = new HashMap<Byte, String>();

   public Key getKey(byte index)
   {
      return keys.get(index);
   }

   public int getSize()
   {
      return keys.size();
   }

   @Override
   public void afterPropertiesSet() throws Exception
   {
      super.afterPropertiesSet();
      for (Byte key : rawkeys.keySet())
      {
         byte[] decoded = Base64.decodeBase64(rawkeys.get(key));
         byte[] output = doFinal(decoded);
         keys.put(key, new SecretKeySpec(output, ICryptoConstants.KEY_ALGORITHM));
      }
   }

   @Override
   protected int getCipherMode()
   {
      return Cipher.DECRYPT_MODE;
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
   {
      Map<String, Properties> propertiesBeans = applicationContext.getBeansOfType(Properties.class);
      for (String k : propertiesBeans.keySet())
      {
         Properties properties = propertiesBeans.get(k);
         for (String key : properties.stringPropertyNames())
         {
            if (key.startsWith("crypto.key"))
            {
               byte index = (byte) Integer.parseInt(key.substring(11));
               rawkeys.put(index, properties.getProperty(key));
            }
         }
      }
   }
}
