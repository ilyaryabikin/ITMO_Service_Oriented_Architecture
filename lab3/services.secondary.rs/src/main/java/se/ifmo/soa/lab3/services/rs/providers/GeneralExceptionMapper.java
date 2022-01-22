package se.ifmo.soa.lab3.services.rs.providers;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exception) {
    final Response.Status internalServerErrorStatus = INTERNAL_SERVER_ERROR;
    return Response.status(internalServerErrorStatus)
        .entity(ErrorResponseBuilder.createErrorResponse(exception, internalServerErrorStatus))
        .build();
  }
}
