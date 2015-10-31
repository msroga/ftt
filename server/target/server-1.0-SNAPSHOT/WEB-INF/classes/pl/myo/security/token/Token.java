package pl.myo.security.token;

/**
 * author: rafal.glowacz@solsoft.pl
 * created: 2010-09-07
 */
public interface Token {
   String getPrincipal();

   long getExpiryTime();
}
