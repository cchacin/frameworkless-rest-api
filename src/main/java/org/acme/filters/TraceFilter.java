package org.acme.filters;

import static org.acme.context.AppContext.TRACE_ID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.acme.context.AppContext;

@WebFilter(urlPatterns = "/*")
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        var traceId = httpRequest.getHeader(AppContext.TRACE_ID_HEADER);
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }
        ScopedValue.where(TRACE_ID, traceId)
                .run(
                        () -> {
                            try {
                                httpResponse.setHeader(AppContext.TRACE_ID_HEADER, TRACE_ID.get());
                                chain.doFilter(request, response); // Continue filter chain
                            } catch (Exception e) {
                                // Log exception
                            }
                        });
    }
}
