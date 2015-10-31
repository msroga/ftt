package pl.ftt.security.token;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pl.ftt.security.crypto.encryptor.BasicEncryptor;
import pl.ftt.security.crypto.exception.EncryptorException;
import pl.ftt.security.crypto.exception.TokenParseException;
import pl.ftt.security.token.converter.TokenConverter;
import pl.ftt.security.token.encoder.TokenEncoder;

/**
 * author: rafal.glowacz@solsoft.pl
 * created: 2010-09-07
 */
public class TokenMarshaller implements InitializingBean{
    public static final String BEAN_NAME = "tokenMarshaller";

    private BasicEncryptor tokenCrypto;

    private TokenEncoder tokenEncoder;

    private TokenConverter<Token> tokenConverter;

    public Token unmarshall(String token) throws TokenParseException
    {
        assertNotEmpty(token);
        byte[] result = new byte[0];
        try
        {
            result = tokenCrypto.decrypt(tokenEncoder.convert(token));
        }
        catch (EncryptorException e)
        {
            throw new TokenParseException(e.getMessage(), e);
        }
        return tokenConverter.convert(new String(result));
    }

    public String marshall(Token token) throws TokenParseException
    {
        String convertedTokenString = tokenConverter.convert(token);
        assertNotEmpty(convertedTokenString);
        byte[] result = new byte[0];
        try
        {
            result = tokenCrypto.encrypt(convertedTokenString.getBytes());
        }
        catch (EncryptorException e)
        {
            throw new TokenParseException(e.getMessage(), e);
        }
        return tokenEncoder.convert(result);
    }

    @SuppressWarnings(
            {
                    "rawtypes", "unchecked"
            })
    public void setTokenConverter(TokenConverter tokenConverter)
    {
        this.tokenConverter = tokenConverter;
    }

    public void setTokenCrypto(BasicEncryptor tokenCrypto)
    {
        this.tokenCrypto = tokenCrypto;
    }

    public void setTokenEncoder(TokenEncoder tokenEncoder)
    {
        this.tokenEncoder = tokenEncoder;
    }

    private void assertNotEmpty(String token) throws TokenParseException
    {
        if (!StringUtils.hasText(token))
        {
            throw new TokenParseException("Token cannot be decrypted because it is empty!");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull(tokenConverter, "Token converter is required");
        Assert.notNull(tokenCrypto, "Token crypto is required");
        Assert.notNull(tokenEncoder, "Token encoder is required");
    }
}
