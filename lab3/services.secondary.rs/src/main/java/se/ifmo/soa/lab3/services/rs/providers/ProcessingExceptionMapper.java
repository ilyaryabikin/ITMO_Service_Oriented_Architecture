package se.ifmo.soa.lab3.services.rs.providers;

import static javax.ws.rs.core.Response.Status.BAD_GATEWAY;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

  @Override
  public Response toResponse(final ProcessingException exception) {
    final Status badGatewayStatus = BAD_GATEWAY;
    return Response.status(badGatewayStatus)
        .entity(
            ErrorResponseBuilder.createErrorResponse(
                badGatewayStatus, exception.getCause().getMessage()))
        .build();
  }
}
