package se.ifmo.soa.lab3.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class CoordinatesDto implements Serializable {

  private int x;

  private Long y;
}
