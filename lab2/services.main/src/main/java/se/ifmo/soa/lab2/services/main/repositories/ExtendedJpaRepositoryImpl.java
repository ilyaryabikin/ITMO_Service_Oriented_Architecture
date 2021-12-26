package se.ifmo.soa.lab2.services.main.repositories;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class ExtendedJpaRepositoryImpl<T, ID extends Serializable>
    extends SimpleJpaRepository<T, ID> implements ExtendedJpaRepository<T, ID> {

  private final EntityManager entityManager;

  public ExtendedJpaRepositoryImpl(
      final JpaEntityInformation<T, ?> jpaEntityInformation, final EntityManager entityManager) {
    super(jpaEntityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public int deleteAll(final Specification<T> specification) {
    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaDelete<T> delete = builder.createCriteriaDelete(getDomainClass());
    final Root<T> root = delete.from(getDomainClass());

    final Predicate predicate = specification.toPredicate(root, builder.createQuery(), builder);
    if (predicate != null) {
      delete.where(predicate);
    }

    final Query query = entityManager.createQuery(delete);
    return query.executeUpdate();
  }
}
