package se.ifmo.soa.lab1.entities;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import se.ifmo.soa.lab1.entities.enums.Country;
import se.ifmo.soa.lab1.entities.enums.EyeColor;
import se.ifmo.soa.lab1.entities.enums.HairColor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Person {

  @Size(max = 511)
  @NotBlank
  private String name;

  @DecimalMin("1")
  private double height;

  @Enumerated(STRING)
  private EyeColor eyeColor;

  @Enumerated(STRING)
  private HairColor hairColor;

  @Enumerated(STRING)
  private Country nationality;

  @Embedded @NotNull @Valid private Location location;
}
