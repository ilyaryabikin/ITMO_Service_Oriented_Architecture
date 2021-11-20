package se.ifmo.soa.lab1.dao;

import java.util.Optional;
import javax.validation.Valid;
import se.ifmo.soa.lab1.exceptions.EntityAttributeIsInvalidException;

public interface EntityDao<K, V> {

  V merge(final @Valid V entity);

  PageResult<V> getAll();

  PageResult<V> getAll(
      final @Valid PageParams pageParams,
      final @Valid SortParams sortParams,
      final @Valid FilterParams filterParams)
      throws EntityAttributeIsInvalidException;

  Optional<V> get(final K id);

  boolean delete(final K id);

  boolean isExists(final K id);
}
