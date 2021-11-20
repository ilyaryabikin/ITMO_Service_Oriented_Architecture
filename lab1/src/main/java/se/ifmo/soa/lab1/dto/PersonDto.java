package se.ifmo.soa.lab1.dto;

import javax.json.bind.annotation.JsonbPropertyOrder;
import lombok.Data;
import se.ifmo.soa.lab1.entities.enums.Country;
import se.ifmo.soa.lab1.entities.enums.EyeColor;
import se.ifmo.soa.lab1.entities.enums.HairColor;

@Data
@JsonbPropertyOrder({"name", "height", "eyeColor", "hairColor", "nationality", "location"})
public class PersonDto {

  private String name;

  private double height;

  private EyeColor eyeColor;

  private HairColor hairColor;

  private Country nationality;

  private LocationDto location;
}
