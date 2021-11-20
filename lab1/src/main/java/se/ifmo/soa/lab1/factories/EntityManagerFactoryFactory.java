package se.ifmo.soa.lab1.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerFactoryFactory {

  @Produces
  @ApplicationScoped
  public EntityManagerFactory getEntityManagerFactory() {
    return Persistence.createEntityManagerFactory("default");
  }
}
