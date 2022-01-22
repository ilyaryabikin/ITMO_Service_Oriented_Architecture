package se.ifmo.soa.lab3.services.main.mappers;

import se.ifmo.soa.domain.Coordinates;
import se.ifmo.soa.domain.Movie;
import se.ifmo.soa.domain.Person;
import se.ifmo.soa.lab3.dto.CoordinatesDto;
import se.ifmo.soa.lab3.dto.MovieDto;
import se.ifmo.soa.lab3.dto.PersonDto;

public class MovieMapper implements EntityMapper<Movie, MovieDto> {

  private final EntityMapper<Coordinates, CoordinatesDto> coordinatesMapper;
  private final EntityMapper<Person, PersonDto> personMapper;

  public MovieMapper(
      final EntityMapper<Coordinates, CoordinatesDto> coordinatesMapper,
      final EntityMapper<Person, PersonDto> personMapper) {
    this.coordinatesMapper = coordinatesMapper;
    this.personMapper = personMapper;
  }

  @Override
  public Movie mapEntityFromDto(final MovieDto dto) {
    final Movie entity = new Movie();
    entity.setName(dto.getName());
    if (dto.getCreationDate() != null) {
      entity.setCreationDate(dto.getCreationDate());
    }
    entity.setOscarsCount(dto.getOscarsCount());
    entity.setBudget(dto.getBudget());
    entity.setLength(dto.getLength());
    entity.setGenre(dto.getGenre());
    if (dto.getCoordinates() != null) {
      entity.setCoordinates(coordinatesMapper.mapEntityFromDto(dto.getCoordinates()));
    }
    if (dto.getScreenwriter() != null) {
      entity.setScreenwriter(personMapper.mapEntityFromDto(dto.getScreenwriter()));
    }
    return entity;
  }

  @Override
  public MovieDto mapDtoFromEntity(final Movie entity) {
    final MovieDto dto = new MovieDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    if (entity.getCreationDate() != null) {
      dto.setCreationDate(entity.getCreationDate());
    }
    dto.setOscarsCount(entity.getOscarsCount());
    dto.setBudget(entity.getBudget());
    dto.setLength(entity.getLength());
    dto.setGenre(entity.getGenre());
    if (entity.getCoordinates() != null) {
      dto.setCoordinates(coordinatesMapper.mapDtoFromEntity(entity.getCoordinates()));
    }
    if (entity.getScreenwriter() != null) {
      dto.setScreenwriter(personMapper.mapDtoFromEntity(entity.getScreenwriter()));
    }
    return dto;
  }
}
