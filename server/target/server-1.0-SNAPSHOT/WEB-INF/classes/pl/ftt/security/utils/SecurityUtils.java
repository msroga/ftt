package pl.ftt.security.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * User: rafal.glowacz
 * Date: 10/10/13
 */
public class SecurityUtils
{

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    public static boolean isExcludedPath(List<String> excludeList, String path) {
        for (String requestPath : excludeList) {
            if (PATH_MATCHER.match(requestPath, path)) {
                return true;
            }
        }
        return false;
    }


    public static List<String> buildExcludeList(String listProperty) {
        return null;
    }

}
