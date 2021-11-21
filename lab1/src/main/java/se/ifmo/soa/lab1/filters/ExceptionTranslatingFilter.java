package se.ifmo.soa.lab1.filters;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static se.ifmo.soa.lab1.utils.ConstraintViolationMessageBuilder.buildErrorMessage;

import java.io.IOException;
import java.time.Instant;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import se.ifmo.soa.lab1.dto.ErrorResponse;
import se.ifmo.soa.lab1.exceptions.RestfulException;

@WebFilter(filterName = "ExceptionTranslatingFilter")
@Slf4j
public class ExceptionTranslatingFilter implements Filter {

  @Inject private Jsonb jsonb;

  @Override
  public void init(final FilterConfig filterConfig) {}

  @Override
  public void destroy() {}

  @Override
  public void doFilter(
      final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    Status errorStatus;
    ErrorResponse errorResponse;
    try {
      chain.doFilter(request, response);
      return;
    } catch (final RestfulException e) {
      log.warn(e.getMessage());
      errorStatus = e.getResponseStatus();
      errorResponse = createErrorResponse(e, errorStatus, request);
    } catch (final ConstraintViolationException e) {
      log.warn(e.getMessage());
      errorStatus = BAD_REQUEST;
      errorResponse =
          createErrorResponse(buildErrorMessage(e.getConstraintViolations()), errorStatus, request);
    } catch (final JsonbException e) {
      log.warn(e.getMessage());
      errorStatus = BAD_REQUEST;
      errorResponse = createErrorResponse(e, errorStatus, request);
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
      errorStatus = INTERNAL_SERVER_ERROR;
      errorResponse = createErrorResponse(e, errorStatus, request);
      log.error("Error occurred during processing of request", e);
    }
    response.setContentType(APPLICATION_JSON);
    ((HttpServletResponse) response).setStatus(errorResponse.getCode());
    jsonb.toJson(errorResponse, response.getWriter());
  }

  private ErrorResponse createErrorResponse(
      final Exception e, final Status responseStatus, final ServletRequest request) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(responseStatus.getStatusCode());
    errorResponse.setReason(responseStatus.getReasonPhrase());
    errorResponse.setMessage(
        e.getMessage() != null ? e.getMessage() : responseStatus.getReasonPhrase());
    errorResponse.setTimestamp(Instant.now());
    if (request instanceof HttpServletRequest) {
      errorResponse.setPath(((HttpServletRequest) request).getRequestURL().toString());
    }
    return errorResponse;
  }

  private ErrorResponse createErrorResponse(
      final String message, final Status responseStatus, final ServletRequest request) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(responseStatus.getStatusCode());
    errorResponse.setReason(responseStatus.getReasonPhrase());
    errorResponse.setMessage(message);
    errorResponse.setTimestamp(Instant.now());
    errorResponse.setPath(((HttpServletRequest) request).getRequestURL().toString());
    return errorResponse;
  }
}
