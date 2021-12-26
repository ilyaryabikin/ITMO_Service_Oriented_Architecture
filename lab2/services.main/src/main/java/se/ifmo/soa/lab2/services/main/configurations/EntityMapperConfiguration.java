package se.ifmo.soa.lab2.services.main.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.ifmo.soa.lab2.services.main.mappers.CoordinatesMapper;
import se.ifmo.soa.lab2.services.main.mappers.LocationMapper;
import se.ifmo.soa.lab2.services.main.mappers.MovieMapper;
import se.ifmo.soa.lab2.services.main.mappers.PersonMapper;

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
