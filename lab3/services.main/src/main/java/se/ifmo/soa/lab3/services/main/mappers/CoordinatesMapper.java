package se.ifmo.soa.lab3.services.main.mappers;

import se.ifmo.soa.domain.Coordinates;
import se.ifmo.soa.lab3.dto.CoordinatesDto;

public class CoordinatesMapper implements EntityMapper<Coordinates, CoordinatesDto> {

  @Override
  public Coordinates mapEntityFromDto(final CoordinatesDto dto) {
    final Coordinates entity = new Coordinates();
    entity.setX(dto.getX());
    entity.setY(dto.getY());
    return entity;
  }

  @Override
  public CoordinatesDto mapDtoFromEntity(final Coordinates entity) {
    final CoordinatesDto dto = new CoordinatesDto();
    dto.setX(entity.getX());
    dto.setY(entity.getY());
    return dto;
  }
}
