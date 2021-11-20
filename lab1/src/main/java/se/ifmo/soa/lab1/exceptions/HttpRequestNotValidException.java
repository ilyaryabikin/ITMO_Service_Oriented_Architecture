package se.ifmo.soa.lab1.exceptions;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class HttpRequestNotValidException extends RestfulException {

  public HttpRequestNotValidException() {
    super(BAD_REQUEST);
  }

  public HttpRequestNotValidException(final String message) {
    super(BAD_REQUEST, message);
  }

  public HttpRequestNotValidException(final String message, final Throwable cause) {
    super(BAD_REQUEST, message, cause);
  }
}
