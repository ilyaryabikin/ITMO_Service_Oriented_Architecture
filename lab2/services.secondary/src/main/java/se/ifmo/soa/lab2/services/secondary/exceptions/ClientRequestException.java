package se.ifmo.soa.lab2.services.secondary.exceptions;

public class ClientRequestException extends RuntimeException {

  public ClientRequestException(final String message) {
    super(message);
  }

  public ClientRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ClientRequestException(final Throwable cause) {
    super(cause);
  }
}
