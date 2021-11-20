package se.ifmo.soa.lab1.entities;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import se.ifmo.soa.lab1.entities.enums.MovieGenre;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Movie {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "name", length = 1023, nullable = false)
  @Size(max = 1023)
  @NotBlank
  private String name;

  @Embedded
  @AttributeOverride(name = "x", column = @Column(name = "coordinates_x", nullable = false))
  @AttributeOverride(name = "y", column = @Column(name = "coordinates_y", nullable = false))
  @NotNull
  @Valid
  private Coordinates coordinates;

  @Column(name = "creation_date")
  @NotNull
  private LocalDate creationDate = LocalDate.now();

  @Column(name = "oscars_count", nullable = false)
  @Min(1)
  @NotNull
  private Long oscarsCount;

  @Column(name = "budget")
  @Min(1)
  private Long budget;

  @Column(name = "length")
  @Min(1)
  private long length;

  @Column(name = "genre", nullable = false)
  @Enumerated(STRING)
  @NotNull
  private MovieGenre genre;

  @Embedded
  @AttributeOverride(
      name = "name",
      column = @Column(name = "screenwriter_name", length = 511, nullable = false))
  @AttributeOverride(
      name = "height",
      column = @Column(name = "screenwriter_height", nullable = false))
  @AttributeOverride(name = "eyeColor", column = @Column(name = "screenwriter_eye_color"))
  @AttributeOverride(name = "hairColor", column = @Column(name = "screenwriter_hair_color"))
  @AttributeOverride(name = "nationality", column = @Column(name = "screenwriter_nationality"))
  @AttributeOverride(
      name = "location.x",
      column = @Column(name = "screenwriter_location_x", nullable = false))
  @AttributeOverride(
      name = "location.y",
      column = @Column(name = "screenwriter_location_y", nullable = false))
  @AttributeOverride(
      name = "location.z",
      column = @Column(name = "screenwriter_location_z", nullable = false))
  @AttributeOverride(
      name = "location.name",
      column = @Column(name = "screenwriter_location_name", length = 874))
  @NotNull
  @Valid
  private Person screenwriter;
}
