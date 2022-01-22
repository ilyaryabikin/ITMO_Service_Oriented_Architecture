package se.ifmo.soa.lab3.services.main.repositories;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
import se.ifmo.soa.domain.enums.Country;
import se.ifmo.soa.domain.enums.EyeColor;
import se.ifmo.soa.domain.enums.HairColor;
import se.ifmo.soa.domain.enums.MovieGenre;

@NoArgsConstructor
public class SpecificationBuilder {

  public static <T> Specification<T> fromFilterParams(final FilterParams filterParams)
      throws InvalidDataAccessApiUsageException {
    Specification<T> specification = Specification.where(null);
    for (final FilterParams.FilterEntry filterEntry : filterParams.getFilterEntries()) {
      specification = specification.and(fromFilterEntry(filterEntry));
    }
    return specification;
  }

  private static <T> Specification<T> fromFilterEntry(final FilterParams.FilterEntry filterEntry)
      throws InvalidDataAccessApiUsageException {
    return (root, query, criteriaBuilder) -> {
      final Path<?> path = getComplexPath(root, filterEntry.getPropertyName());
      return criteriaBuilder.equal(
          path, getSafeValue(path.getJavaType(), filterEntry.getPropertyValue()));
    };
  }

  private static Path<?> getComplexPath(final Root<?> root, final String complexPath) {
    final String[] paths = complexPath.split("\\.");
    Path<?> path = root.get(paths[0]);
    for (int i = 1; i < paths.length; i++) {
      path = path.get(paths[i]);
    }
    return path;
  }

  private static Object getSafeValue(final Class<?> pathJavaType, final String value) {
    if (Enum.class.isAssignableFrom(pathJavaType)) {
      if (pathJavaType == EyeColor.class) {
        return EyeColor.valueOf(value);
      } else if (pathJavaType == HairColor.class) {
        return HairColor.valueOf(value);
      } else if (pathJavaType == Country.class) {
        return Country.valueOf(value);
      } else if (pathJavaType == MovieGenre.class) {
        return MovieGenre.valueOf(value);
      }
      return null;
    }
    return value;
  }
}
