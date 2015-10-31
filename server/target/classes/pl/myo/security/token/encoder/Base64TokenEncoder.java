package pl.myo.security.token.encoder;

import org.apache.commons.codec.binary.Base64;

public class Base64TokenEncoder implements TokenEncoder {

    @Override
    public String convert(byte[] token)
    {
        return new String(Base64.encodeBase64(token, false, true));
    }

    @Override
    public byte[] convert(String token)
    {
        return Base64.decodeBase64(token.getBytes());
    }
}