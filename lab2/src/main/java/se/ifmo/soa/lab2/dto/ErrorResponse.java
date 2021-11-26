package se.ifmo.soa.lab2.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class ErrorResponse {

  private int code;

  private String reason;

  private String message;

  private Instant timestamp;

  private String path;
}
