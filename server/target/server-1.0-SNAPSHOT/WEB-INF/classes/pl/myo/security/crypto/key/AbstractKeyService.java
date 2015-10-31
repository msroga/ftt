package pl.myo.security.crypto.key;

import org.springframework.beans.factory.InitializingBean;
import pl.myo.security.crypto.ICryptoConstants;
import pl.myo.security.crypto.IMasterKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

/**
 * author: rafal.glowacz@solsoft.pl
 * created: 2011-01-05
 */
public abstract class AbstractKeyService implements InitializingBean
{

   private Cipher masterCipher;

   @Override
   public void afterPropertiesSet() throws Exception
   {
      masterCipher = Cipher.getInstance(ICryptoConstants.KEY_ALGORITHM);
      SecretKeySpec masterSecretKey = new SecretKeySpec(IMasterKey.MASTER_SECRET,
              ICryptoConstants.KEY_ALGORITHM);
      masterCipher.init(getCipherMode(), masterSecretKey);
   }

   protected byte[] doFinal(byte[] input) throws IllegalBlockSizeException, BadPaddingException
   {
      return masterCipher.doFinal(input);
   }

   protected abstract int getCipherMode();
}
