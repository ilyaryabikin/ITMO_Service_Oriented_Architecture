package se.ifmo.soa.lab2.dto;

import static java.lang.Math.min;

import java.util.List;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

  private List<T> result;

  private int pageNumber;

  private int pageSize;

  private int lastPage;

  private long totalCount;

  public static <T> PageResult<T> fromPage(final Page<T> page) {
    final PageResult<T> pageResult = new PageResult<>();
    pageResult.setResult(page.getContent());
    pageResult.setPageNumber(page.getNumber());
    pageResult.setPageSize(page.getSize());
    pageResult.setLastPage(min(0, page.getTotalPages() - 1));
    pageResult.setTotalCount(page.getTotalElements());
    return pageResult;
  }

  public static <U, T> PageResult<U> fromPage(
      final Page<T> page, final Function<? super T, ? extends U> mappingFunction) {
    return fromPage(page.map(mappingFunction));
  }
}
