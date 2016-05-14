package net.quizz.auth.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author lutfun
 * @since 5/12/16
 */
public class AuthenticationFilter implements Filter {

    private List<String> excludeUrlPatterns;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludeUrlPatternsStr = filterConfig.getInitParameter("exclude-url-patterns");
        excludeUrlPatterns = Pattern.compile(",")
                .splitAsStream(excludeUrlPatternsStr)
                .collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getContextPath();
        int contextPathLen = (contextPath != null) ? contextPath.length() : 0;
        final String relativeUri = request.getRequestURI().substring(contextPathLen);

        List<String> startWiths = Arrays.asList("/css", "/images");
        List<String> endsWiths = Arrays.asList(".css", ".js");

        if (excludeUrlPatterns.stream().anyMatch(relativeUri::startsWith)
                || startWiths.stream().anyMatch(relativeUri::startsWith)
                || endsWiths.stream().anyMatch(relativeUri::endsWith)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER") == null) {
            response.sendRedirect(contextPath + "/auth/signin");
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
