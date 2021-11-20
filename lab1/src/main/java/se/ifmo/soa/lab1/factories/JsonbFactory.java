package se.ifmo.soa.lab1.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class JsonbFactory {

  @Produces
  @ApplicationScoped
  public Jsonb getJsonb() {
    return JsonbBuilder.create();
  }
}
