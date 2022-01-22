package se.ifmo.soa.lab3.services.rs.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.ifmo.soa.lab3.services.secondary.ejb.api.MoviesService;
import se.ifmo.soa.lab3.services.secondary.ejb.exceptions.ErrorResponseException;

@Path("/api/oscar")
public class MoviesResource {

  @EJB private MoviesService moviesService;

  @GET
  @Path("/movies/get-losers")
  @Produces(APPLICATION_JSON)
  public Response getMoviesLosers(
      final @QueryParam("page") Integer page, final @QueryParam("size") Integer size) {
    try {
      return Response.ok(moviesService.getMoviesLosers(page, size)).build();
    } catch (final ErrorResponseException e) {
      return Response.status(e.getErrorResponse().getCode()).entity(e.getErrorResponse()).build();
    }
  }

  @GET
  @Path("/directors/get-losers")
  @Produces(APPLICATION_JSON)
  public Response getScreenwritersLosers(
      final @QueryParam("page") Integer page, final @QueryParam("size") Integer size) {
    try {
      return Response.ok(moviesService.getScreenwritersLosers(page, size)).build();
    } catch (final ErrorResponseException e) {
      return Response.status(e.getErrorResponse().getCode()).entity(e.getErrorResponse()).build();
    }
  }
}
