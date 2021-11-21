package se.ifmo.soa.lab1.servlets;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
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
import se.ifmo.soa.lab1.dao.FilterParams;
import se.ifmo.soa.lab1.dao.MovieDao;
import se.ifmo.soa.lab1.dao.PageParams;
import se.ifmo.soa.lab1.dao.PageResult;
import se.ifmo.soa.lab1.dao.SortParams;
import se.ifmo.soa.lab1.dto.MovieDto;
import se.ifmo.soa.domain.Movie;
import se.ifmo.soa.lab1.exceptions.EntityAttributeIsInvalidException;
import se.ifmo.soa.lab1.exceptions.HttpMethodNotSupported;
import se.ifmo.soa.lab1.exceptions.HttpRequestNotValidException;
import se.ifmo.soa.lab1.exceptions.RestfulException;
import se.ifmo.soa.lab1.mappers.MovieMapper;
import se.ifmo.soa.lab1.utils.RequestParamUtils;

@WebServlet(urlPatterns = "/api/movies")
public class MovieServlet extends HttpServlet {

  @Inject private MovieDao movieDao;
  @Inject private MovieMapper movieMapper;
  @Inject private Jsonb jsonb;

  @Override
  protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final MovieDto movieDto = jsonb.fromJson(req.getReader(), MovieDto.class);
    final Movie movie = movieMapper.mapEntityFromDto(movieDto);

    final Movie savedMovie = movieDao.merge(movie);

    resp.setStatus(SC_CREATED);
    jsonb.toJson(movieMapper.mapDtoFromEntity(savedMovie), resp.getWriter());
  }

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final PageParams pageParams = RequestParamUtils.getPageParams(req);
    final SortParams sortParams = RequestParamUtils.getSortParams(req);
    final FilterParams filterParams = RequestParamUtils.getFilterParams(req);

    try {
      final PageResult<Movie> pageResult = movieDao.getAll(pageParams, sortParams, filterParams);
      jsonb.toJson(pageResult.map(movieMapper::mapDtoFromEntity), resp.getWriter());
    } catch (final EntityAttributeIsInvalidException e) {
      throw new HttpRequestNotValidException(e.getMessage(), e);
    }
  }

  @Override
  protected void doPut(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    throw new HttpMethodNotSupported();
  }

  @Override
  protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final FilterParams filterParams = RequestParamUtils.getFilterParams(req);

    try {
      final int result = movieDao.deleteByScreenwriterParams(filterParams);
      if (result >= 1) {
        resp.setStatus(SC_NO_CONTENT);
      } else {
        throw new RestfulException(NOT_FOUND, "Movies matching specified criteria was not found");
      }
    } catch (final EntityAttributeIsInvalidException e) {
      throw new HttpRequestNotValidException(e.getMessage(), e);
    }
  }
}
