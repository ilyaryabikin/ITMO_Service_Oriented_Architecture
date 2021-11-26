package se.ifmo.soa.lab2.exceptions;

import org.springframework.http.HttpStatus;

public class RestfulException extends RuntimeException {

  private final HttpStatus responseStatus;

  public RestfulException(final HttpStatus responseStatus) {
    this.responseStatus = responseStatus;
  }

  public RestfulException(final HttpStatus responseStatus, final String message) {
    super(message);
    this.responseStatus = responseStatus;
  }

  public RestfulException(
      final HttpStatus responseStatus, final String message, final Throwable cause) {
    super(message, cause);
    this.responseStatus = responseStatus;
  }

  public HttpStatus getResponseStatus() {
    return responseStatus;
  }
}
