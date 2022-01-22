package se.ifmo.soa.lab3.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;

@Data
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {

  private int code;

  private String reason;

  private String message;

  private Instant timestamp;

  private String path;
}
