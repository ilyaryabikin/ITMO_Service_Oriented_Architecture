package se.ifmo.soa.lab2.services.secondary.providers;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static se.ifmo.soa.lab2.services.secondary.providers.ErrorResponseBuilder.createErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import se.ifmo.soa.lab2.services.secondary.exceptions.ClientRequestException;

@Provider
public class ClientRequestExceptionMapper implements ExceptionMapper<ClientRequestException> {

  @Override
  public Response toResponse(final ClientRequestException exception) {
    final Status internalServerError = INTERNAL_SERVER_ERROR;
    return Response.status(internalServerError)
        .entity(createErrorResponse(exception, internalServerError))
        .build();
  }
}
