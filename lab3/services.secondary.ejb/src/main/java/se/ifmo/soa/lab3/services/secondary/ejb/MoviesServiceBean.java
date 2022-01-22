package se.ifmo.soa.lab3.services.secondary.ejb;

import static java.util.logging.Level.FINER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_MAX_ENTITY_SIZE;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.PAYLOAD_ANY;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import se.ifmo.soa.lab3.dto.ErrorResponse;
import se.ifmo.soa.lab3.dto.MovieDto;
import se.ifmo.soa.lab3.dto.PageResult;
import se.ifmo.soa.lab3.dto.PersonDto;
import se.ifmo.soa.lab3.services.secondary.ejb.api.MoviesService;
import se.ifmo.soa.lab3.services.secondary.ejb.exceptions.ErrorResponseException;
import se.ifmo.soa.lab3.services.secondary.ejb.providers.ObjectMapperProvider;

@Stateless
public class MoviesServiceBean implements MoviesService {

  @Resource(name = "main-service-url")
  private String mainServiceUrl;

  @Resource(name = "movies-endpoint")
  private String moviesEndpoint;

  @Resource(name = "losers-oscars-count")
  private int losersOscarsCount;

  private final Client client =
      ClientBuilder.newBuilder()
          .register(ObjectMapperProvider.class)
          .register(JacksonFeature.class)
          .register(
              new LoggingFeature(
                  Logger.getLogger(getClass().getName()),
                  FINER,
                  PAYLOAD_ANY,
                  DEFAULT_MAX_ENTITY_SIZE))
          .build();

  public PageResult<MovieDto> getMoviesLosers(final Integer page, final Integer size)
      throws ErrorResponseException, ProcessingException {
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
        return response.readEntity(new GenericType<PageResult<MovieDto>>() {});
      } else {
        final ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        addMissingInfo(errorResponse, response);
        throw new ErrorResponseException(errorResponse);
      }
    }
  }

  public PageResult<PersonDto> getScreenwritersLosers(final Integer page, final Integer size)
      throws ErrorResponseException, ProcessingException {
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
        return response
            .readEntity(new GenericType<PageResult<MovieDto>>() {})
            .map(MovieDto::getScreenwriter);
      } else {
        final ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        addMissingInfo(errorResponse, response);
        throw new ErrorResponseException(errorResponse);
      }
    }
  }

  private void addMissingInfo(final ErrorResponse errorResponse, final Response response) {
    if (errorResponse.getCode() == 0) {
      errorResponse.setCode(response.getStatus());
    }
    if (errorResponse.getReason() == null) {
      errorResponse.setReason(response.getStatusInfo().getReasonPhrase());
    }
    if (errorResponse.getPath() == null) {
      errorResponse.setPath(response.getLocation().toString());
    }
  }
}
