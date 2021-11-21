package se.ifmo.soa.lab1.dto;

import java.time.LocalDate;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import lombok.Data;
import se.ifmo.soa.domain.enums.MovieGenre;

@Data
@JsonbPropertyOrder({
  "id",
  "name",
  "coordinates",
  "creationDate",
  "oscarsCount",
  "budget",
  "length",
  "genre",
  "screenwriter"
})
public class MovieDto {

  private Long id;

  private String name;

  private CoordinatesDto coordinates;

  private LocalDate creationDate;

  private Long oscarsCount;

  private Long budget;

  private long length;

  private MovieGenre genre;

  private PersonDto screenwriter;

  @JsonbTransient
  public void setId(final Long id) {
    this.id = id;
  }
}
