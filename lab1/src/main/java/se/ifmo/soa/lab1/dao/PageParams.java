package se.ifmo.soa.lab1.dao;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {

  public static final String PAGE_NUMBER_PARAM = "page";
  public static final String PAGE_SIZE_PARAM = "size";
  public static final int DEFAULT_PAGE_SIZE = 20;

  @Min(0)
  private int page;

  @Min(1)
  private int size = DEFAULT_PAGE_SIZE;
}
