package se.ifmo.soa.lab1.utils;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static se.ifmo.soa.lab1.dao.PageParams.PAGE_NUMBER_PARAM;
import static se.ifmo.soa.lab1.dao.PageParams.PAGE_SIZE_PARAM;
import static se.ifmo.soa.lab1.dao.SortDirection.ASC;
import static se.ifmo.soa.lab1.dao.SortParams.SORT_PARAM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import se.ifmo.soa.lab1.dao.FilterParams;
import se.ifmo.soa.lab1.dao.FilterParams.FilterEntry;
import se.ifmo.soa.lab1.dao.PageParams;
import se.ifmo.soa.lab1.dao.SortDirection;
import se.ifmo.soa.lab1.dao.SortParams;
import se.ifmo.soa.lab1.dao.SortParams.SortEntry;
import se.ifmo.soa.lab1.exceptions.HttpRequestNotValidException;

@NoArgsConstructor(access = PRIVATE)
public class RequestParamUtils {

  private static final Set<String> PAGE_AND_SORT_PARAMS =
      Collections.unmodifiableSet(
          new HashSet<>(Arrays.asList(PAGE_NUMBER_PARAM, PAGE_SIZE_PARAM, SORT_PARAM)));

  public static PageParams getPageParams(final HttpServletRequest request)
      throws HttpRequestNotValidException {
    final PageParams pageParams = new PageParams();
    try {
      if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
        pageParams.setPage(Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM)));
      }

      if (request.getParameter(PAGE_SIZE_PARAM) != null) {
        pageParams.setSize(Integer.parseInt(request.getParameter(PAGE_SIZE_PARAM)));
      }
    } catch (final NumberFormatException e) {
      throw new HttpRequestNotValidException(e.getMessage(), e);
    }
    return pageParams;
  }

  public static SortParams getSortParams(final HttpServletRequest request) {
    final List<SortEntry> sortEntries = new ArrayList<>();
    final String[] sortParameterValues = request.getParameterValues(SORT_PARAM);
    for (final String sortParam :
        sortParameterValues != null ? sortParameterValues : new String[0]) {
      try {
        final String[] stringEntry = sortParam.split(",");
        if (stringEntry.length == 1) {
          sortEntries.add(new SortEntry(stringEntry[0], ASC));
        } else if (stringEntry.length == 2) {
          sortEntries.add(new SortEntry(stringEntry[0], SortDirection.fromString(stringEntry[1])));
        } else {
          throw new HttpRequestNotValidException(
              format(
                  "Could not parse sort params from request param '%s' : invalid size", sortParam));
        }
      } catch (final IllegalArgumentException e) {
        throw new HttpRequestNotValidException(e.getMessage(), e);
      }
    }
    return new SortParams(sortEntries);
  }

  public static FilterParams getFilterParams(final HttpServletRequest request) {
    final Map<String, String[]> parameterMap = request.getParameterMap();
    final List<FilterEntry> filterEntries = new ArrayList<>();
    for (final Entry<String, String[]> entry : parameterMap.entrySet()) {
      if (PAGE_AND_SORT_PARAMS.contains(entry.getKey())) {
        continue;
      }
      if (entry.getValue().length != 1) {
        throw new HttpRequestNotValidException(
            String.format(
                "Could not parse filter param %s with several filter values : %s",
                entry.getKey(), Arrays.toString(entry.getValue())));
      }
      filterEntries.add(new FilterEntry(entry.getKey(), entry.getValue()[0]));
    }
    return new FilterParams(filterEntries);
  }

  public static Long getIdPathVariable(final HttpServletRequest request)
      throws HttpRequestNotValidException {
    try {
      return Long.parseLong(getLastPathPart(request));
    } catch (final NumberFormatException | ArrayIndexOutOfBoundsException e) {
      throw new HttpRequestNotValidException("Incorrect value for id path variable", e);
    }
  }

  private static String getLastPathPart(final HttpServletRequest request) {
    final String[] pathParts = request.getPathInfo().split("/");
    return pathParts[pathParts.length - 1];
  }
}
