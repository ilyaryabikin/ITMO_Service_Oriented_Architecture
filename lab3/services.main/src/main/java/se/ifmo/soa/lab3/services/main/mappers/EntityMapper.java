package se.ifmo.soa.lab3.services.main.mappers;

public interface EntityMapper<E, D> {

  E mapEntityFromDto(D dto);

  D mapDtoFromEntity(E entity);
}
