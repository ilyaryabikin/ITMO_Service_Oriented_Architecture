package se.ifmo.soa.lab3.services.main.controllers;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.ifmo.soa.domain.Movie;
import se.ifmo.soa.domain.Movie_;
import se.ifmo.soa.lab3.dto.MovieDto;
import se.ifmo.soa.lab3.dto.PageResult;
import se.ifmo.soa.lab3.services.main.exceptions.HttpRequestNotValidException;
import se.ifmo.soa.lab3.services.main.exceptions.RestfulException;
import se.ifmo.soa.lab3.services.main.mappers.MovieMapper;
import se.ifmo.soa.lab3.services.main.repositories.FilterParams;
import se.ifmo.soa.lab3.services.main.repositories.MovieRepository;
import se.ifmo.soa.lab3.services.main.repositories.SpecificationBuilder;

@RestController
@RequestMapping(path = "/api/movies")
@Validated
@RequiredArgsConstructor
public class MovieController {

  private final MovieRepository movieRepository;
  private final MovieMapper movieMapper;

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @Transactional(readOnly = true)
  public PageResult<MovieDto> getAll(
      final FilterParams filterParams,
      final @PageableDefault(size = 20, sort = Movie_.ID, direction = ASC) Pageable pageable) {
    try {
      final Specification<Movie> movieSpecification =
          SpecificationBuilder.fromFilterParams(filterParams);
      final Page<Movie> movies = movieRepository.findAll(movieSpecification, pageable);
      return PageResultUtils.fromPage(movies, movieMapper::mapDtoFromEntity);
    } catch (final InvalidDataAccessApiUsageException e) {
      throw new HttpRequestNotValidException(e.getMessage(), e);
    }
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(CREATED)
  @Transactional
  public MovieDto save(final @RequestBody MovieDto movieDto) {
    final Movie movie = validatedMapFromDto(movieDto);
    final Movie savedMovie = movieRepository.save(movie);
    return movieMapper.mapDtoFromEntity(savedMovie);
  }

  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  @Transactional
  public void delete(final FilterParams filterParams) {
    try {
      final Specification<Movie> movieSpecification =
          SpecificationBuilder.fromFilterParams(filterParams);
      if (movieRepository.deleteAll(movieSpecification) <= 0) {
        throw new RestfulException(NOT_FOUND, "Movies matching specified criteria was not found");
      }
    } catch (final InvalidDataAccessApiUsageException e) {
      throw new HttpRequestNotValidException(e.getMessage(), e);
    }
  }

  @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
  @Transactional(readOnly = true)
  public MovieDto get(final @PathVariable Long id) {
    final Movie movie =
        movieRepository
            .findById(id)
            .orElseThrow(() -> new RestfulException(NOT_FOUND, notFoundMessage(id)));
    return movieMapper.mapDtoFromEntity(movie);
  }

  @GetMapping(path = "/genres/min", produces = APPLICATION_JSON_VALUE)
  @Transactional(readOnly = true)
  public MovieDto getByMinGenre() {
    final Movie movie =
        movieRepository
            .findFirstByOrderByGenreAsc()
            .orElseThrow(
                () -> new RestfulException(NOT_FOUND, "Movie with min genre was not found"));
    return movieMapper.mapDtoFromEntity(movie);
  }

  @GetMapping(path = "/lengths/max", produces = APPLICATION_JSON_VALUE)
  @Transactional(readOnly = true)
  public MovieDto getByMaxLength() {
    final Movie movie =
        movieRepository
            .findFirstByOrderByLengthDesc()
            .orElseThrow(
                () -> new RestfulException(NOT_FOUND, "Movie with max length was not found"));
    return movieMapper.mapDtoFromEntity(movie);
  }

  @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Transactional
  public MovieDto update(final @PathVariable Long id, final @RequestBody MovieDto movieDto) {
    if (!movieRepository.existsById(id)) {
      throw new RestfulException(NOT_FOUND, notFoundMessage(id));
    }
    final Movie movie = validatedMapFromDto(movieDto);
    movie.setId(id);
    final Movie updatedMovie = movieRepository.save(movie);
    return movieMapper.mapDtoFromEntity(updatedMovie);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(NO_CONTENT)
  @Transactional
  public void delete(final @PathVariable Long id) {
    if (!movieRepository.existsById(id)) {
      throw new RestfulException(NOT_FOUND, notFoundMessage(id));
    }
    movieRepository.deleteById(id);
  }

  private String notFoundMessage(final Long id) {
    return "Movie with id " + id + " was not found";
  }

  private @Valid Movie validatedMapFromDto(final MovieDto movieDto) {
    return movieMapper.mapEntityFromDto(movieDto);
  }
}
