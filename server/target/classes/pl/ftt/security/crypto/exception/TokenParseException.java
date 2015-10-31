package pl.ftt.security.crypto.exception;

public class TokenParseException extends Exception {

    public TokenParseException(String message) {
        super(message);
    }

    public TokenParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
