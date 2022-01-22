package se.ifmo.soa.lab3.services.main.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import se.ifmo.soa.domain.Movie;

@Repository
public interface MovieRepository extends ExtendedJpaRepository<Movie, Long> {

  Optional<Movie> findFirstByOrderByGenreAsc();

  Optional<Movie> findFirstByOrderByLengthDesc();
}
