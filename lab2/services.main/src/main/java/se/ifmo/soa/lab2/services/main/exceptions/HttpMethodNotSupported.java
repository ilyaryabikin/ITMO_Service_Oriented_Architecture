package se.ifmo.soa.lab2.services.main.exceptions;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

public class HttpMethodNotSupported extends RestfulException {

  public HttpMethodNotSupported() {
    super(METHOD_NOT_ALLOWED);
  }
}
