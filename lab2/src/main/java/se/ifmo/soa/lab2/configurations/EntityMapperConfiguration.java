package se.ifmo.soa.lab2.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.ifmo.soa.lab2.mappers.CoordinatesMapper;
import se.ifmo.soa.lab2.mappers.LocationMapper;
import se.ifmo.soa.lab2.mappers.MovieMapper;
import se.ifmo.soa.lab2.mappers.PersonMapper;

@Configuration
public class EntityMapperConfiguration {

  @Bean
  public CoordinatesMapper coordinatesMapper() {
    return new CoordinatesMapper();
  }

  @Bean
  public LocationMapper locationMapper() {
    return new LocationMapper();
  }

  @Bean
  public PersonMapper personMapper(final LocationMapper locationMapper) {
    return new PersonMapper(locationMapper);
  }

  @Bean
  public MovieMapper movieMapper(
      final CoordinatesMapper coordinatesMapper, final PersonMapper personMapper) {
    return new MovieMapper(coordinatesMapper, personMapper);
  }
}
