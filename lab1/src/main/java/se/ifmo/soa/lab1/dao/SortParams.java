package se.ifmo.soa.lab1.dao;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class SortParams {

  public static final String SORT_PARAM = "sort";

  private final List<SortEntry> sortEntries;

  public SortParams(final List<SortEntry> sortEntries) {
    this.sortEntries = sortEntries;
  }

  public SortParams() {
    this.sortEntries = new ArrayList<>();
  }

  @Data
  public static class SortEntry {
    private final String propertyName;
    private final SortDirection sortDirection;
  }
}
