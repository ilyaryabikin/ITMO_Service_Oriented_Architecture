package se.ifmo.soa.lab1.servlets;

import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.IOException;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se.ifmo.soa.lab1.dao.MovieDao;
import se.ifmo.soa.lab1.dto.MovieDto;
import se.ifmo.soa.lab1.entities.Movie;
import se.ifmo.soa.lab1.exceptions.HttpMethodNotSupported;
import se.ifmo.soa.lab1.exceptions.RestfulException;
import se.ifmo.soa.lab1.mappers.MovieMapper;
import se.ifmo.soa.lab1.utils.RequestParamUtils;

@WebServlet(urlPatterns = "/api/movies/*")
public class SpecificMovieServlet extends HttpServlet {

  @Inject private MovieDao movieDao;
  @Inject private MovieMapper movieMapper;
  @Inject private Jsonb jsonb;

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final Long id = RequestParamUtils.getIdPathVariable(req);
    final Movie entity =
        movieDao
            .get(id)
            .orElseThrow(
                () ->
                    new RestfulException(
                        NOT_FOUND, String.format("Movie with id %d was not found", id)));

    jsonb.toJson(movieMapper.mapDtoFromEntity(entity), resp.getWriter());
  }

  @Override
  protected void doPut(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final Long id = RequestParamUtils.getIdPathVariable(req);
    final MovieDto movieDto = jsonb.fromJson(req.getReader(), MovieDto.class);
    final Movie movie = movieMapper.mapEntityFromDto(movieDto);

    if (!movieDao.isExists(id)) {
      throw new RestfulException(NOT_FOUND, String.format("Movie with id %d was not found", id));
    }

    movie.setId(id);
    final Movie updatedMovie = movieDao.merge(movie);

    jsonb.toJson(movieMapper.mapDtoFromEntity(updatedMovie), resp.getWriter());
  }

  @Override
  protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final Long id = RequestParamUtils.getIdPathVariable(req);

    final boolean wasDeleted = movieDao.delete(id);
    if (!wasDeleted) {
      throw new RestfulException(NOT_FOUND, String.format("Movie with id %d was not found", id));
    }

    resp.setStatus(SC_NO_CONTENT);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new HttpMethodNotSupported();
  }
}
