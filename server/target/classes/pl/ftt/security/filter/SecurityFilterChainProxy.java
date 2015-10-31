package pl.ftt.security.filter;

import org.springframework.security.web.FilterChainProxy;
import pl.ftt.security.utils.SecurityUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * author: rafal.glowacz@solsoft.pl
 * created: 2010-09-07
 */
public class SecurityFilterChainProxy extends FilterChainProxy {

    private List<String> excludeList;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestPath = request.getRequestURI().replace(request.getContextPath(), "");
        if (SecurityUtils.isExcludedPath(excludeList, requestPath))
        {
            chain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            super.doFilter(servletRequest, servletResponse, chain);
        }
    }

    public void setExcludeList(List<String> excludeList)
    {
        this.excludeList = excludeList;
    }
}
