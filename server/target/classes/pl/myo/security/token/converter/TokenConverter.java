package pl.myo.security.token.converter;

import pl.myo.security.crypto.exception.TokenParseException;

/**
 * author: rafal.glowacz@solsoft.pl
 * created: 2010-09-07
 */
public interface TokenConverter<T> {

    boolean supports(String token);

    @SuppressWarnings("rawtypes")
    boolean supports(Class tokenClass);

    T convert(String token) throws TokenParseException;

    String convert(T token) throws TokenParseException;
}
