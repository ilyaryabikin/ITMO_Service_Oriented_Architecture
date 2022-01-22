package se.ifmo.soa.lab3.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import se.ifmo.soa.domain.enums.MovieGenre;

@Data
public class MovieDto implements Serializable {

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
