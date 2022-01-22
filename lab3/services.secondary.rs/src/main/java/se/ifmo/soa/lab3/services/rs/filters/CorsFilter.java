package se.ifmo.soa.lab3.services.rs.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

  @Override
  public void filter(
      final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
      throws IOException {
    responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
    requestContext.getHeaders().add("Access-Control-Allow-Methods", "GET, HEAD");
    requestContext.getHeaders().add("Access-Control-Allow-Headers", "*");
  }
}
