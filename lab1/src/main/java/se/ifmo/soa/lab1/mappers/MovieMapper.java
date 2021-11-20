package se.ifmo.soa.lab1.mappers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import se.ifmo.soa.lab1.dto.CoordinatesDto;
import se.ifmo.soa.lab1.dto.MovieDto;
import se.ifmo.soa.lab1.dto.PersonDto;
import se.ifmo.soa.lab1.entities.Coordinates;
import se.ifmo.soa.lab1.entities.Movie;
import se.ifmo.soa.lab1.entities.Person;

@ApplicationScoped
public class MovieMapper implements EntityMapper<Movie, MovieDto> {

  @Inject private EntityMapper<Coordinates, CoordinatesDto> coordinatesMapper;
  @Inject private EntityMapper<Person, PersonDto> personMapper;

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

  /*@Override
  public void updateEntityFromDto(final MovieDto dto, final Movie entityToUpdate) {
    if (dto != null) {
      if (dto.getName() != null && !dto.getName().equals(entityToUpdate.getName())) {
        entityToUpdate.setName(dto.getName());
      }
      if (dto.getCreationDate() != null
          && !dto.getCreationDate().equals(entityToUpdate.getCreationDate())) {
        entityToUpdate.setCreationDate(dto.getCreationDate());
      }
      if (dto.getOscarsCount() != null
          && !dto.getOscarsCount().equals(entityToUpdate.getOscarsCount())) {
        entityToUpdate.setOscarsCount(dto.getOscarsCount());
      }
      if (dto.getBudget() != null && !dto.getBudget().equals(entityToUpdate.getBudget())) {
        entityToUpdate.setBudget(dto.getBudget());
      }
      if (dto.getLength() != entityToUpdate.getLength()) {
        entityToUpdate.setLength(dto.getLength());
      }
      if (dto.getGenre() != null && !dto.getGenre().equals(entityToUpdate.getGenre())) {
        entityToUpdate.setGenre(dto.getGenre());
      }
    }
  }*/
}
