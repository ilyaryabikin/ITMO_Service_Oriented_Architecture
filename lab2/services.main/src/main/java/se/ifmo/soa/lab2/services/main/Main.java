package se.ifmo.soa.lab2.services.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
    return applicationBuilder(builder);
  }

  public static void main(final String[] args) {
    applicationBuilder(new SpringApplicationBuilder()).run(args);
  }

  private static SpringApplicationBuilder applicationBuilder(
      final SpringApplicationBuilder builder) {
    return builder.sources(Main.class);
  }
}
