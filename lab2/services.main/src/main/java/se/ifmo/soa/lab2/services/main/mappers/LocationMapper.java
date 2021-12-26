package se.ifmo.soa.lab2.services.main.mappers;

import se.ifmo.soa.domain.Location;
import se.ifmo.soa.lab2.dto.LocationDto;

public class LocationMapper implements EntityMapper<Location, LocationDto> {

  @Override
  public Location mapEntityFromDto(LocationDto dto) {
    final Location entity = new Location();
    entity.setX(dto.getX());
    entity.setY(dto.getY());
    entity.setZ(dto.getZ());
    entity.setName(dto.getName());
    return entity;
  }

  @Override
  public LocationDto mapDtoFromEntity(final Location entity) {
    final LocationDto dto = new LocationDto();
    dto.setX(entity.getX());
    dto.setY(entity.getY());
    dto.setZ(entity.getZ());
    dto.setName(entity.getName());
    return dto;
  }
}
