package se.ifmo.soa.lab1.dao;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FilterParams {

  private final List<FilterEntry> filterEntries;

  public FilterParams() {
    this.filterEntries = new ArrayList<>();
  }

  public FilterParams(final List<FilterEntry> filterEntries) {
    this.filterEntries = filterEntries;
  }

  @Data
  public static class FilterEntry {
    private final String propertyName;
    private final String propertyValue;
  }
}
