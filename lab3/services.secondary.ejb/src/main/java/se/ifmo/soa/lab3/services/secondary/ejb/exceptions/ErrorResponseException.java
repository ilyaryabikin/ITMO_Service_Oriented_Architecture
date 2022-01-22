package se.ifmo.soa.lab3.services.secondary.ejb.exceptions;

import se.ifmo.soa.lab3.dto.ErrorResponse;

public class ErrorResponseException extends Exception {

  private final ErrorResponse errorResponse;

  public ErrorResponseException(final ErrorResponse errorResponse) {
    this.errorResponse = errorResponse;
  }

  public ErrorResponse getErrorResponse() {
    return errorResponse;
  }
}
