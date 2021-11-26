package se.ifmo.soa.lab2.dto;

import lombok.Data;
import se.ifmo.soa.domain.enums.Country;
import se.ifmo.soa.domain.enums.EyeColor;
import se.ifmo.soa.domain.enums.HairColor;

@Data
public class PersonDto {

  private String name;

  private double height;

  private EyeColor eyeColor;

  private HairColor hairColor;

  private Country nationality;

  private LocationDto location;
}
