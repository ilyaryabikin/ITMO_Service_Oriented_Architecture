package se.ifmo.soa.lab1.servlets;

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
import se.ifmo.soa.domain.Movie;
import se.ifmo.soa.lab1.exceptions.RestfulException;
import se.ifmo.soa.lab1.mappers.MovieMapper;

@WebServlet("/api/movies/lengths/max")
public class MaxLengthMovieGenre extends HttpServlet {

  @Inject private MovieDao movieDao;
  @Inject private MovieMapper movieMapper;
  @Inject private Jsonb jsonb;

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final Movie maxLengthMovie =
        movieDao
            .getByMaxLength()
            .orElseThrow(
                () -> new RestfulException(NOT_FOUND, "Movie with max length was not found"));

    jsonb.toJson(movieMapper.mapDtoFromEntity(maxLengthMovie), resp.getWriter());
  }
}
