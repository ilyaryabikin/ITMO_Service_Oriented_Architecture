package se.ifmo.soa.lab2.services.secondary.resources;

import static java.util.logging.Level.FINER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_LOGGER_NAME;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_MAX_ENTITY_SIZE;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.PAYLOAD_ANY;

import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.logging.LoggingFeature;
import se.ifmo.soa.lab2.dto.ErrorResponse;
import se.ifmo.soa.lab2.dto.MovieDto;
import se.ifmo.soa.lab2.dto.PageResult;
import se.ifmo.soa.lab2.services.secondary.exceptions.ClientRequestException;

@Path("/api/oscar")
public class MoviesResource {

  private String mainServiceUrl;
  private String moviesEndpoint;
  private int losersOscarsCount;

  private final Client client =
      ClientBuilder.newBuilder()
          .register(
              new LoggingFeature(
                  Logger.getLogger(DEFAULT_LOGGER_NAME),
                  FINER,
                  PAYLOAD_ANY,
                  DEFAULT_MAX_ENTITY_SIZE))
          .build();

  @Context
  public void setServletContext(final ServletContext servletContext) {
    mainServiceUrl = servletContext.getInitParameter("mainServiceUrl");
    moviesEndpoint = servletContext.getInitParameter("moviesEndpoint");
    losersOscarsCount = Integer.parseInt(servletContext.getInitParameter("losersOscarsCount"));
  }

  @GET
  @Path("/movies/get-losers")
  @Produces(APPLICATION_JSON)
  public Response getMoviesLosers(
      final @QueryParam("page") Integer page, final @QueryParam("size") Integer size) {
    try (final Response response =
        client
            .target(mainServiceUrl)
            .path(moviesEndpoint)
            .queryParam("oscarsCount", losersOscarsCount)
            .queryParam("page", page == null ? 0 : page)
            .queryParam("size", size == null ? 0 : size)
            .request(APPLICATION_JSON)
            .get()) {
      if (response.getStatusInfo().getFamily() == SUCCESSFUL) {
        return ok(response.readEntity(new GenericType<PageResult<MovieDto>>() {})).build();
      } else {
        final ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        return status(errorResponse.getCode()).entity(errorResponse).build();
      }
    } catch (final Exception e) {
      throw new ClientRequestException(e.getMessage(), e);
    }
  }

  @GET
  @Path("/directors/get-losers")
  @Produces(APPLICATION_JSON)
  public Response getScreenwritersLosers(
      final @QueryParam("page") Integer page, final @QueryParam("size") Integer size) {
    try (final Response response =
        client
            .target(mainServiceUrl)
            .path(moviesEndpoint)
            .queryParam("oscarsCount", losersOscarsCount)
            .queryParam("page", page == null ? 0 : page)
            .queryParam("size", size == null ? 0 : size)
            .request(APPLICATION_JSON)
            .get()) {
      if (response.getStatusInfo().getFamily() == SUCCESSFUL) {
        return ok(response
                .readEntity(new GenericType<PageResult<MovieDto>>() {})
                .map(MovieDto::getScreenwriter))
            .build();
      } else {
        final ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        return status(errorResponse.getCode()).entity(errorResponse).build();
      }
    } catch (final Exception e) {
      throw new ClientRequestException(e.getMessage(), e);
    }
  }
}
