package se.ifmo.soa.lab1.dto;

import java.time.Instant;
import javax.json.bind.annotation.JsonbPropertyOrder;
import lombok.Data;

@Data
@JsonbPropertyOrder({"code", "reason", "message", "timestamp", "path"})
public class ErrorResponse {

  private int code;

  private String reason;

  private String message;

  private Instant timestamp;

  private String path;
}
