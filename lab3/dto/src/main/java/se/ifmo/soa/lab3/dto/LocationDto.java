package se.ifmo.soa.lab3.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class LocationDto implements Serializable {

  private int x;

  private Integer y;

  private Float z;

  private String name;
}
