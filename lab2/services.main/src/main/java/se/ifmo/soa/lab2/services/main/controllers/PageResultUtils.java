package se.ifmo.soa.lab2.services.main.controllers;

import static java.lang.Math.max;
import static lombok.AccessLevel.PRIVATE;

import java.util.function.Function;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import se.ifmo.soa.lab2.dto.PageResult;

@NoArgsConstructor(access = PRIVATE)
public class PageResultUtils {

  public static <T> PageResult<T> fromPage(final Page<T> page) {
    final PageResult<T> pageResult = new PageResult<>();
    pageResult.setResult(page.getContent());
    pageResult.setPageNumber(page.getNumber());
    pageResult.setPageSize(page.getSize());
    pageResult.setLastPage(max(0, page.getTotalPages() - 1));
    pageResult.setTotalCount(page.getTotalElements());
    return pageResult;
  }

  public static <U, T> PageResult<U> fromPage(
      final Page<T> page, final Function<? super T, ? extends U> mappingFunction) {
    return fromPage(page.map(mappingFunction));
  }
}
