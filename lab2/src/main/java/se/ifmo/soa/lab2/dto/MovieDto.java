package se.ifmo.soa.lab2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;
import se.ifmo.soa.domain.enums.MovieGenre;

@Data
public class MovieDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  private String name;

  private CoordinatesDto coordinates;

  private LocalDate creationDate;

  private Long oscarsCount;

  private Long budget;

  private long length;

  private MovieGenre genre;

  private PersonDto screenwriter;
}
