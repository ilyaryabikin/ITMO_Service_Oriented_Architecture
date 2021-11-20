package se.ifmo.soa.lab1.dto;

import javax.json.bind.annotation.JsonbPropertyOrder;
import lombok.Data;

@Data
@JsonbPropertyOrder({"x", "y", "z", "name"})
public class LocationDto {

  private int x;

  private Integer y;

  private Float z;

  private String name;
}
