package pl.ftt.security.filter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import pl.ftt.security.crypto.exception.TokenParseException;
import pl.ftt.security.token.Token;
import pl.ftt.security.token.TokenMarshaller;
import pl.ftt.security.token.TrustedUsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.Assert.hasLength;

public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean implements InitializingBean
{
//   private static Logger LOGGER = Logger.getLogger(AbstractAuthenticationProcessingFilter.class);

   private static final String AUTH_SCHEME_REGEX = "%s\\stoken=\"(.*)\"";

   private AuthenticationManager authenticationManager;

   protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

   protected TokenMarshaller marshaller;

   private Pattern authSchemePattern;

   protected String tokenName;

   private int tokenLengthLimit;

   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
         ServletException
   {

      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;

      if (!requiresAuthentication())
      {
         chain.doFilter(request, response);

         return;
      }

//      if (LOGGER.isDebugEnabled())
//      {
//         LOGGER.debug("Request is to process authentication");
//      }

      Authentication authResult;

      authResult = attemptAuthentication(request);
      if (authResult != null && authResult.isAuthenticated())
      {
         SecurityContextHolder.getContext().setAuthentication(authResult);
      }

      chain.doFilter(request, response);

      return;
   }

   protected boolean requiresAuthentication()
   {
      return SecurityContextHolder.getContext().getAuthentication() == null;
   }

   protected final Authentication attemptAuthentication(HttpServletRequest request)
   {
      try
      {
         String token = getToken(request);
         if (isTokenStringValid(token))
         {
            Authentication authentication = getAuthentication(request, token);
            Authentication afterAuthentication = authenticationManager.authenticate(authentication);
            logSuccessAuthentication(afterAuthentication);
            return afterAuthentication;
         }
      }
      catch (AuthenticationException e)
      {
         logFailureAuthentication(e);
         handleAuthenticationException();
      }
      return null;
   }

   protected Authentication getAuthentication(HttpServletRequest request, String token) throws AuthenticationException
   {
      try
      {
         Token userToken = marshaller.unmarshall(token);
         UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new TrustedUsernamePasswordAuthenticationToken(
               userToken.getPrincipal(),
               token);
         setDetails(request, usernamePasswordAuthenticationToken);
         return usernamePasswordAuthenticationToken;
      }
      catch (TokenParseException e)
      {
         throw new InsufficientAuthenticationException("Insufficient token");
      }
   }

   protected abstract String getToken(HttpServletRequest request);

   protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest)
   {
      authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
   }

   protected void logSuccessAuthentication(Authentication authentication)
   {
//      if (authentication != null && LOGGER.isDebugEnabled())
//      {
//         LOGGER.debug("Authentication success: " + authentication.toString());
//      }
   }

   protected void logFailureAuthentication(AuthenticationException ex)
   {
//      if (LOGGER.isDebugEnabled())
//      {
//         LOGGER.debug("Authentication failure", ex);
//      }
   }

   protected void logAuthorizationToken(String token)
   {
//      if (LOGGER.isDebugEnabled())
//      {
//         LOGGER.debug("Authorization token: " + token);
//      }
   }

   protected boolean isSchemeSupported(String header)
   {
      return authSchemePattern.matcher(header).matches();
   }

   private boolean isTokenStringValid(String token)
   {
      if (StringUtils.hasText(token))
      {
         return !isLengthLimitSet() || token.length() <= tokenLengthLimit;
      }
      else
      {
         return false;
      }
   }

   private boolean isLengthLimitSet()
   {
      return tokenLengthLimit > 0;
   }

   private void handleAuthenticationException() throws AuthenticationException
   {
      SecurityContextHolder.getContext().setAuthentication(null);
   }

   protected String getTokenValue(String token)
   {
      Matcher tokenMatcher = authSchemePattern.matcher(token);
      if (!tokenMatcher.matches())
      {
         return null;
      }
      return tokenMatcher.group(1);
   }

   public void setTokenName(String tokenName)
   {
      this.tokenName = tokenName;
   }

   public void setTokenLengthLimit(int tokenLengthLimit)
   {
      this.tokenLengthLimit = tokenLengthLimit;
   }

   public void setMarshaller(TokenMarshaller marshaller)
   {
      this.marshaller = marshaller;
   }

   public void setAuthenticationManager(AuthenticationManager authenticationManager)
   {
      this.authenticationManager = authenticationManager;
   }

   public void setAuthenticationDetailsSource(
         AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource)
   {
      Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
      this.authenticationDetailsSource = authenticationDetailsSource;
   }

   @Override
   public void afterPropertiesSet()
   {
      Assert.notNull(tokenName, "Token name is required");
      Assert.notNull(marshaller, "Token marshaller is required");
      Assert.notNull(authenticationManager, "Authentication manager is required");
      hasLength(tokenName);
      authSchemePattern = Pattern.compile(String.format(AUTH_SCHEME_REGEX, tokenName));
   }
}
