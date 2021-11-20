package se.ifmo.soa.lab1.dto;

import javax.json.bind.annotation.JsonbPropertyOrder;
import lombok.Data;

@Data
@JsonbPropertyOrder({"x", "y"})
public class CoordinatesDto {

  private int x;

  private Long y;
}
