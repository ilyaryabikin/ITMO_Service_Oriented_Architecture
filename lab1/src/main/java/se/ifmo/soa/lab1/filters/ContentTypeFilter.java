package se.ifmo.soa.lab1.filters;

import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNSUPPORTED_MEDIA_TYPE;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import se.ifmo.soa.lab1.exceptions.RestfulException;

@WebFilter(filterName = "ContentTypeFilter")
public class ContentTypeFilter implements Filter {

  @Override
  public void init(final FilterConfig filterConfig) {}

  @Override
  public void destroy() {}

  @Override
  public void doFilter(
      final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      final HttpServletRequest httpRequest = (HttpServletRequest) request;
      if (isHavingBody(httpRequest) && isHavingInvalidContentType(httpRequest)) {
        throw new RestfulException(
            UNSUPPORTED_MEDIA_TYPE,
            String.format(
                "Required Content-Type is %s but was %s",
                APPLICATION_JSON, httpRequest.getContentType()));
      }
    }
    response.setContentType(APPLICATION_JSON);
    chain.doFilter(request, response);
  }

  private boolean isSavingMethod(final HttpServletRequest httpRequest) {
    return Objects.equals(httpRequest.getMethod(), POST)
        || Objects.equals(httpRequest.getMethod(), PUT);
  }

  private boolean isHavingBody(final HttpServletRequest httpRequest) throws IOException {
    return httpRequest.getReader().ready();
  }

  private boolean isHavingInvalidContentType(final HttpServletRequest httpRequest) {
    final String contentType = httpRequest.getContentType();
    return !Objects.equals(contentType, APPLICATION_JSON);
  }
}
