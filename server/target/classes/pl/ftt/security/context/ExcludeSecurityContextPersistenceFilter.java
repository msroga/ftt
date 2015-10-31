package pl.ftt.security.context;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import pl.ftt.security.utils.SecurityUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * User: rafal.glowacz
 * Date: 10/10/13
 */
public class ExcludeSecurityContextPersistenceFilter extends SecurityContextPersistenceFilter {

    private List<String> excludeList;

    public ExcludeSecurityContextPersistenceFilter(SecurityContextRepository repo)
    {
        super(repo);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestPath = request.getRequestURI().replace(request.getContextPath(), "");
        if (SecurityUtils.isExcludedPath(excludeList, requestPath))
        {
            HttpSession session = request.getSession(false);
            if (session != null)
            {
                session.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            }
            chain.doFilter(req, res);
        }
        else
        {
            super.doFilter(req, res, chain);
        }
    }

    public void setExcludeList(List<String> excludeList)
    {
        this.excludeList = excludeList;
    }
}
