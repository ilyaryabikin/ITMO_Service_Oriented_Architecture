package se.ifmo.soa.lab1.exceptions;

import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;

public class HttpMethodNotSupported extends RestfulException {

  public HttpMethodNotSupported() {
    super(METHOD_NOT_ALLOWED);
  }
}
