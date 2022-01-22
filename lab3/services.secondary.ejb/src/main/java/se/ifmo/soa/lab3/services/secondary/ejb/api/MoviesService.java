package se.ifmo.soa.lab3.services.secondary.ejb.api;

import javax.ejb.Remote;
import javax.ws.rs.ProcessingException;
import se.ifmo.soa.lab3.dto.MovieDto;
import se.ifmo.soa.lab3.dto.PageResult;
import se.ifmo.soa.lab3.dto.PersonDto;
import se.ifmo.soa.lab3.services.secondary.ejb.exceptions.ErrorResponseException;

@Remote
public interface MoviesService {

  PageResult<MovieDto> getMoviesLosers(final Integer page, final Integer size)
      throws ErrorResponseException, ProcessingException;

  PageResult<PersonDto> getScreenwritersLosers(final Integer page, final Integer size)
      throws ErrorResponseException, ProcessingException;
}
