package se.ifmo.soa.lab2.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.Data;

@Data
@JsonInclude(NON_NULL)
public class ErrorResponse {

  private int code;

  private String reason;

  private String message;

  private Instant timestamp;

  private String path;
}
