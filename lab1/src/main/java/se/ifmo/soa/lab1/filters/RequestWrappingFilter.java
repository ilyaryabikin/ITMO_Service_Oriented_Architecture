package se.ifmo.soa.lab1.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import se.ifmo.soa.lab1.utils.CachedDataHttpServletRequest;

@WebFilter(filterName = "RequestWrappingFilter")
public class RequestWrappingFilter implements Filter {

  @Override
  public void init(final FilterConfig filterConfig) {}

  @Override
  public void destroy() {}

  @Override
  public void doFilter(
      final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      final CachedDataHttpServletRequest cachedRequest =
          new CachedDataHttpServletRequest((HttpServletRequest) request);
      chain.doFilter(cachedRequest, response);
      return;
    }
    chain.doFilter(request, response);
  }
}
