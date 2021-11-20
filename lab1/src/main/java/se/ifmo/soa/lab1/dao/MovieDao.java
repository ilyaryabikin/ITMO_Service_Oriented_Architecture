package se.ifmo.soa.lab1.dao;

import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static se.ifmo.soa.lab1.dao.SortDirection.ASC;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import se.ifmo.soa.lab1.dao.FilterParams.FilterEntry;
import se.ifmo.soa.lab1.dao.SortParams.SortEntry;
import se.ifmo.soa.lab1.entities.Movie;
import se.ifmo.soa.lab1.entities.enums.Country;
import se.ifmo.soa.lab1.entities.enums.EyeColor;
import se.ifmo.soa.lab1.entities.enums.HairColor;
import se.ifmo.soa.lab1.entities.enums.MovieGenre;
import se.ifmo.soa.lab1.exceptions.EntityAttributeIsInvalidException;

@ApplicationScoped
public class MovieDao implements EntityDao<Long, Movie> {

  @Inject private EntityManagerFactory entityManagerFactory;

  @Override
  public Movie merge(final @Valid Movie entity) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    final Movie mergedEntity = entityManager.merge(entity);
    transaction.commit();

    entityManager.close();

    return mergedEntity;
  }

  @Override
  public PageResult<Movie> getAll() {
    try {
      return getAll(new PageParams(), new SortParams(), new FilterParams());
    } catch (final EntityAttributeIsInvalidException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public PageResult<Movie> getAll(
      final @Valid PageParams pageParams,
      final @Valid SortParams sortParams,
      final @Valid FilterParams filterParams)
      throws EntityAttributeIsInvalidException {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    final CriteriaQuery<Movie> movieCriteriaQuery = criteriaBuilder.createQuery(Movie.class);
    final Root<Movie> root = movieCriteriaQuery.from(Movie.class);
    movieCriteriaQuery.select(root);

    final PageResult<Movie> pageResult = new PageResult<>();
    try {
      final List<Predicate> predicates =
          getPredicatesFromFilterParams(filterParams, criteriaBuilder, root);

      movieCriteriaQuery.where(predicates.toArray(new Predicate[0]));

      for (final SortEntry sortEntry : sortParams.getSortEntries()) {
        if (sortEntry.getSortDirection() == ASC) {
          movieCriteriaQuery.orderBy(
              criteriaBuilder.asc(getComplexPath(root, sortEntry.getPropertyName())));
        } else {
          movieCriteriaQuery.orderBy(
              criteriaBuilder.desc(getComplexPath(root, sortEntry.getPropertyName())));
        }
      }

      final TypedQuery<Movie> getAllQuery = entityManager.createQuery(movieCriteriaQuery);
      getAllQuery.setFirstResult(pageParams.getPage() * pageParams.getSize());
      getAllQuery.setMaxResults(pageParams.getSize());

      final CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
      countCriteriaQuery
          .select(criteriaBuilder.count(countCriteriaQuery.from(Movie.class)))
          .where(predicates.toArray(new Predicate[0]));
      final TypedQuery<Long> countAllQuery = entityManager.createQuery(countCriteriaQuery);

      final List<Movie> result = getAllQuery.getResultList();
      final long totalCount = countAllQuery.getSingleResult();
      final int lastPage =
          max(0, (int) (ceil(totalCount / max(1, (double) pageParams.getSize()))) - 1);

      pageResult.setResult(result);
      pageResult.setPageNumber(pageParams.getPage());
      pageResult.setPageSize(result.size());
      pageResult.setLastPage(lastPage);
      pageResult.setTotalCount(totalCount);
    } catch (final IllegalArgumentException e) {
      throw new EntityAttributeIsInvalidException(e.getMessage(), e);
    } finally {
      transaction.commit();
      entityManager.close();
    }

    return pageResult;
  }

  @Override
  public Optional<Movie> get(final Long id) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    final Optional<Movie> result = Optional.ofNullable(entityManager.find(Movie.class, id));
    transaction.commit();

    entityManager.close();

    return result;
  }

  public Optional<Movie> getByMinGenre() {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    final String sql = "select m from Movie m order by m.genre asc";
    final TypedQuery<Movie> minGenreQuery = entityManager.createQuery(sql, Movie.class);
    minGenreQuery.setMaxResults(1);

    Optional<Movie> result;
    try {
      result = Optional.ofNullable(minGenreQuery.getSingleResult());
    } catch (final NoResultException e) {
      result = Optional.empty();
    }
    transaction.commit();

    entityManager.close();

    return result;
  }

  public Optional<Movie> getByMaxLength() {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    final String sql = "select m from Movie m order by m.length desc";
    final TypedQuery<Movie> maxLengthQuery = entityManager.createQuery(sql, Movie.class);
    maxLengthQuery.setMaxResults(1);

    Optional<Movie> result;
    try {
      result = Optional.ofNullable(maxLengthQuery.getSingleResult());
    } catch (final NoResultException e) {
      result = Optional.empty();
    }
    transaction.commit();

    entityManager.close();

    return result;
  }

  public int deleteByScreenwriterParams(final @Valid FilterParams filterParams)
      throws EntityAttributeIsInvalidException {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    final CriteriaDelete<Movie> deleteMoviesCriteriaQuery =
        criteriaBuilder.createCriteriaDelete(Movie.class);
    final Root<Movie> root = deleteMoviesCriteriaQuery.from(Movie.class);

    int result;
    try {
      final List<Predicate> predicates =
          getPredicatesFromFilterParams(filterParams, criteriaBuilder, root);

      deleteMoviesCriteriaQuery.where(predicates.toArray(new Predicate[0]));

      final Query deleteQuery = entityManager.createQuery(deleteMoviesCriteriaQuery);
      result = deleteQuery.executeUpdate();
    } catch (final IllegalArgumentException e) {
      throw new EntityAttributeIsInvalidException(e.getMessage(), e);
    } finally {
      transaction.commit();
      entityManager.close();
    }

    return result;
  }

  @Override
  public boolean delete(final Long id) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    final Movie entity = entityManager.find(Movie.class, id);
    if (entity == null) {
      return false;
    }
    entityManager.remove(entity);
    transaction.commit();

    entityManager.close();

    return true;
  }

  @Override
  public boolean isExists(final Long id) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    final String sql = "select count(*) from Movie m where m.id = " + id;
    final TypedQuery<Long> existsQuery = entityManager.createQuery(sql, Long.class);
    final boolean result = existsQuery.getSingleResult() > 0;
    transaction.commit();
    entityManager.close();
    return result;
  }

  private List<Predicate> getPredicatesFromFilterParams(
      final FilterParams filterParams, final CriteriaBuilder criteriaBuilder, final Root<?> root) {
    final List<Predicate> predicates = new ArrayList<>();
    for (final FilterEntry filterEntry : filterParams.getFilterEntries()) {
      final Path<?> path = getComplexPath(root, filterEntry.getPropertyName());
      predicates.add(
          criteriaBuilder.equal(
              getComplexPath(root, filterEntry.getPropertyName()),
              getSafeValue(path, filterEntry.getPropertyValue())));
    }
    return predicates;
  }

  private Object getSafeValue(final Path<?> path, final String value) {
    final Enum<?> enumValue = getEnumValue(path, value);
    if (enumValue == null) {
      return value;
    }
    return enumValue;
  }

  private Enum<?> getEnumValue(final Path<?> path, final String value) {
    final Class<?> type = path.getJavaType();
    if (type == EyeColor.class) {
      return EyeColor.valueOf(value);
    } else if (type == HairColor.class) {
      return HairColor.valueOf(value);
    } else if (type == Country.class) {
      return Country.valueOf(value);
    } else if (type == MovieGenre.class) {
      return MovieGenre.valueOf(value);
    }
    return null;
  }

  private Path<?> getComplexPath(final Root<?> root, final String complexPath) {
    final String[] paths = complexPath.split("\\.");
    Path<?> path = root.get(paths[0]);
    for (int i = 1; i < paths.length; i++) {
      path = path.get(paths[i]);
    }
    return path;
  }
}
