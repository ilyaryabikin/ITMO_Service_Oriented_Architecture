package se.ifmo.soa.lab1.mappers;

public interface EntityMapper<E, D> {

  E mapEntityFromDto(D dto);

  D mapDtoFromEntity(E entity);
}
