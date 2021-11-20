package se.ifmo.soa.lab1.exceptions;

import javax.ws.rs.core.Response.Status;

public class RestfulException extends RuntimeException {

  private final Status responseStatus;

  public RestfulException(final Status responseStatus) {
    this.responseStatus = responseStatus;
  }

  public RestfulException(final Status responseStatus, final String message) {
    super(message);
    this.responseStatus = responseStatus;
  }

  public RestfulException(
      final Status responseStatus, final String message, final Throwable cause) {
    super(message, cause);
    this.responseStatus = responseStatus;
  }

  public Status getResponseStatus() {
    return responseStatus;
  }
}
