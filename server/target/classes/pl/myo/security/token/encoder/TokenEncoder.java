package pl.myo.security.token.encoder;

public interface TokenEncoder {
    String convert(byte[] token);

    byte[] convert(String token);
}
