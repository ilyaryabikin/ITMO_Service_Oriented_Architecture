package se.ifmo.soa.lab1.mappers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import se.ifmo.soa.lab1.dto.LocationDto;
import se.ifmo.soa.lab1.dto.PersonDto;
import se.ifmo.soa.domain.Location;
import se.ifmo.soa.domain.Person;

@ApplicationScoped
public class PersonMapper implements EntityMapper<Person, PersonDto> {

  @Inject private EntityMapper<Location, LocationDto> locationMapper;

  @Override
  public Person mapEntityFromDto(final PersonDto dto) {
    final Person entity = new Person();
    entity.setName(dto.getName());
    entity.setHeight(dto.getHeight());
    entity.setEyeColor(dto.getEyeColor());
    entity.setHairColor(dto.getHairColor());
    entity.setNationality(dto.getNationality());
    if (dto.getLocation() != null) {
      entity.setLocation(locationMapper.mapEntityFromDto(dto.getLocation()));
    }
    return entity;
  }

  @Override
  public PersonDto mapDtoFromEntity(final Person entity) {
    final PersonDto dto = new PersonDto();
    dto.setName(entity.getName());
    dto.setHeight(entity.getHeight());
    dto.setEyeColor(entity.getEyeColor());
    dto.setHairColor(entity.getHairColor());
    dto.setNationality(entity.getNationality());
    if (entity.getLocation() != null) {
      dto.setLocation(locationMapper.mapDtoFromEntity(entity.getLocation()));
    }
    return dto;
  }
}
