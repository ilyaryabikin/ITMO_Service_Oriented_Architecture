package se.ifmo.soa.lab2.services.main.mappers;

public interface EntityMapper<E, D> {

  E mapEntityFromDto(D dto);

  D mapDtoFromEntity(E entity);
}
