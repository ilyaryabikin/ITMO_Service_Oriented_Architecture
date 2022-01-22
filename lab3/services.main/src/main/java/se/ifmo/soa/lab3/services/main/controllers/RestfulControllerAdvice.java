package se.ifmo.soa.lab3.services.main.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.util.WebUtils.ERROR_EXCEPTION_ATTRIBUTE;

import java.time.Instant;
import java.util.Collection;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import se.ifmo.soa.lab3.dto.ErrorResponse;
import se.ifmo.soa.lab3.services.main.exceptions.RestfulException;

@ControllerAdvice
public class RestfulControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(RestfulException.class)
  public ResponseEntity<Object> handleRestfulException(
      final RestfulException exception, final WebRequest webRequest) {
    return handleExceptionInternal(exception, exception.getResponseStatus(), webRequest);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      final ConstraintViolationException exception, final WebRequest webRequest) {
    final String message =
        buildConstraintViolationErrorMessage(exception.getConstraintViolations());
    return handleExceptionInternal(exception, BAD_REQUEST, message, webRequest);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(
      final Exception exception, final WebRequest webRequest) {
    return handleExceptionInternal(exception, INTERNAL_SERVER_ERROR, webRequest);
  }

  protected ResponseEntity<Object> handleExceptionInternal(
      final Exception ex, final HttpStatus status, final WebRequest request) {
    return handleExceptionInternal(ex, null, new HttpHeaders(), status, request);
  }

  protected ResponseEntity<Object> handleExceptionInternal(
      final Exception ex, final HttpStatus status, final String message, final WebRequest request) {
    final ErrorResponse errorResponse = createErrorResponse(status, message, request);
    return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      final Exception ex,
      final @Nullable Object body,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    if (INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute(ERROR_EXCEPTION_ATTRIBUTE, ex, SCOPE_REQUEST);
    }
    return new ResponseEntity<>(
        body != null ? body : createErrorResponse(ex, status, request), headers, status);
  }

  private ErrorResponse createErrorResponse(
      final Exception exception, final HttpStatus status, final WebRequest webRequest) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(status.value());
    errorResponse.setReason(status.getReasonPhrase());
    errorResponse.setMessage(exception.getMessage());
    errorResponse.setTimestamp(Instant.now());
    errorResponse.setPath(((ServletWebRequest) webRequest).getRequest().getRequestURL().toString());
    return errorResponse;
  }

  private ErrorResponse createErrorResponse(
      final HttpStatus status, final String message, final WebRequest webRequest) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(status.value());
    errorResponse.setReason(status.getReasonPhrase());
    errorResponse.setMessage(message);
    errorResponse.setTimestamp(Instant.now());
    errorResponse.setPath(((ServletWebRequest) webRequest).getRequest().getRequestURL().toString());
    return errorResponse;
  }

  private String buildConstraintViolationErrorMessage(
      final Collection<? extends ConstraintViolation<?>> violations) {
    final StringBuilder messageBuilder = new StringBuilder();

    for (final ConstraintViolation<?> violation : violations) {
      final PathImpl propertyPath = (PathImpl) violation.getPropertyPath();
      messageBuilder
          .append("Incorrect value for field '")
          .append(propertyPath.getLeafNode().getName())
          .append("': ")
          .append(violation.getMessage())
          .append("; ");
    }

    return messageBuilder.length() > 0
        ? messageBuilder
            .delete(messageBuilder.lastIndexOf("; "), messageBuilder.length())
            .toString()
        : "";
  }
}
