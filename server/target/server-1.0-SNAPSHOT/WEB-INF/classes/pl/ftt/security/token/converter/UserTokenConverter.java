package pl.ftt.security.token.converter;

import org.apache.commons.lang3.StringUtils;
import pl.ftt.security.crypto.exception.TokenParseException;
import pl.ftt.security.token.UserToken;

/**
 * author: rafal.glowacz@solsoft.pl created: 2010-09-07
 */
public class UserTokenConverter implements TokenConverter<UserToken> {

    private static final String DEFAULT_TOKEN_DELIMITER = ";";

    private String tokenDelimiter = DEFAULT_TOKEN_DELIMITER;

    @Override
    public boolean supports(String token) {
        try {
            return UserToken.USER_TOKEN_KEY.equals(token.split(tokenDelimiter)[0]);
        } catch (Exception e) {
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class tokenClass) {
        return UserToken.class.isAssignableFrom(tokenClass);
    }

    @Override
    public String convert(UserToken token) throws TokenParseException
    {
        if (token == null) {
            return throwTokenParseException();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(UserToken.USER_TOKEN_KEY);
        sb.append(tokenDelimiter);
        sb.append(token.getPrincipal());
        sb.append(tokenDelimiter);
        sb.append(token.getExpiryTime());
        return sb.toString();
    }

    @Override
    public UserToken convert(String token) throws TokenParseException {
        if (token == null || token.isEmpty()) {
            throwTokenParseException();
        }

        String[] splitToken = token.split(tokenDelimiter);
        if (splitToken.length != 3 || !StringUtils.isNumeric(splitToken[2])) {
            throwTokenParseException();
        }
        return new UserToken(splitToken[1], Long.parseLong(splitToken[2]));
    }

    private String throwTokenParseException() throws TokenParseException {
        throw new TokenParseException("Invalid token value");
    }
}
