package se.ifmo.soa.lab2.services.main.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ExtendedJpaRepository<T, ID extends Serializable>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  int deleteAll(final Specification<T> specification);
}
