package wu.huang.hflex.config.logging;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MDCConfig implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            MDC.put("requestId", UUID.randomUUID().toString());
            //MDC.put("userId", getUserIdFromAuth());
            MDC.put("apiName", ((HttpServletRequest) servletRequest).getRequestURI());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}
