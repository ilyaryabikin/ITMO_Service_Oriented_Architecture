package se.ifmo.soa.lab1.dao;

public enum SortDirection {
  ASC,
  DESC;

  public static SortDirection fromString(final String value) {
    for (final SortDirection sortDirection : values()) {
      if (sortDirection.name().equalsIgnoreCase(value)) {
        return sortDirection;
      }
    }
    throw new IllegalArgumentException("No value of type SortDirection for string " + value);
  }
}
