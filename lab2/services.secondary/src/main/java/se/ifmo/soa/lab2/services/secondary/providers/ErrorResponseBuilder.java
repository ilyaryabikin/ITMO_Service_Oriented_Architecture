package se.ifmo.soa.lab2.services.secondary.providers;

import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;
import javax.ws.rs.core.Response.Status;
import lombok.NoArgsConstructor;
import se.ifmo.soa.lab2.dto.ErrorResponse;

@NoArgsConstructor(access = PRIVATE)
public class ErrorResponseBuilder {

  public static ErrorResponse createErrorResponse(final Exception exception, final Status status) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(status.getStatusCode());
    errorResponse.setReason(status.getReasonPhrase());
    errorResponse.setMessage(exception.getMessage());
    errorResponse.setTimestamp(Instant.now());
    // errorResponse.setPath(((ServletWebRequest)
    // webRequest).getRequest().getRequestURL().toString());
    return errorResponse;
  }

  private static ErrorResponse createErrorResponse(final Status status, final String message) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(status.getStatusCode());
    errorResponse.setReason(status.getReasonPhrase());
    errorResponse.setMessage(message);
    errorResponse.setTimestamp(Instant.now());
    // errorResponse.setPath(((ServletWebRequest)
    // webRequest).getRequest().getRequestURL().toString());
    return errorResponse;
  }
}
