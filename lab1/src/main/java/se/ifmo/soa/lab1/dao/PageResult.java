package se.ifmo.soa.lab1.dao;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.json.bind.annotation.JsonbPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonbPropertyOrder({"pageNumber", "pageSize", "totalCount", "lastPage", "result"})
public class PageResult<T> {

  private List<T> result;

  private int pageNumber;

  private int pageSize;

  private int lastPage;

  private long totalCount;

  public <R> PageResult<R> map(final Function<? super T, ? extends R> mappingFunction) {
    return new PageResult<>(
        result.stream().map(mappingFunction).collect(Collectors.toList()),
        pageNumber,
        pageSize,
        lastPage,
        totalCount);
  }
}
