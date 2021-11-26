package se.ifmo.soa.lab2.configurations;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.ifmo.soa.lab2.controllers.FilterParamsArgumentResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new FilterParamsArgumentResolver());
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry
        .addMapping("/api/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
        .allowedHeaders("*");
  }
}
