package se.ifmo.soa.lab3.services.main.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import se.ifmo.soa.lab3.services.main.exceptions.HttpRequestNotValidException;
import se.ifmo.soa.lab3.services.main.repositories.FilterParams;

public class FilterParamsArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String PAGE_NUMBER_PARAM = "page";
  private static final String PAGE_SIZE_PARAM = "size";
  private static final String SORT_PARAM = "sort";

  private static final Set<String> PAGE_AND_SORT_PARAMS =
      Collections.unmodifiableSet(
          new HashSet<>(Arrays.asList(PAGE_NUMBER_PARAM, PAGE_SIZE_PARAM, SORT_PARAM)));

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return parameter.getParameterType() == FilterParams.class;
  }

  @Override
  public Object resolveArgument(
      final MethodParameter parameter,
      final @Nullable ModelAndViewContainer mavContainer,
      final NativeWebRequest webRequest,
      final @Nullable WebDataBinderFactory binderFactory)
      throws Exception {
    final List<FilterParams.FilterEntry> filterEntries = new ArrayList<>();
    for (final Entry<String, String[]> entry : webRequest.getParameterMap().entrySet()) {
      if (PAGE_AND_SORT_PARAMS.contains(entry.getKey())) {
        continue;
      }
      if (entry.getValue().length != 1) {
        throw new HttpRequestNotValidException(
            String.format(
                "Could not parse filter param %s with several filter values : %s",
                entry.getKey(), Arrays.toString(entry.getValue())));
      }
      filterEntries.add(new FilterParams.FilterEntry(entry.getKey(), entry.getValue()[0]));
    }
    return new FilterParams(filterEntries);
  }
}
