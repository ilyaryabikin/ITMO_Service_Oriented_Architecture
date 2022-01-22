package se.ifmo.soa.lab3.dto;

import java.io.Serializable;
import lombok.Data;
import se.ifmo.soa.domain.enums.Country;
import se.ifmo.soa.domain.enums.EyeColor;
import se.ifmo.soa.domain.enums.HairColor;

@Data
public class PersonDto implements Serializable {

  private String name;

  private double height;

  private EyeColor eyeColor;

  private HairColor hairColor;

  private Country nationality;

  private LocationDto location;
}
