package se.ifmo.soa.lab3.services.rs;

import static java.util.logging.Level.FINER;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_LOGGER_NAME;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_MAX_ENTITY_SIZE;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.PAYLOAD_ANY;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import se.ifmo.soa.lab3.services.rs.providers.EjbProvider;

@ApplicationPath("")
public class Main extends ResourceConfig {

  public Main() {
    packages(Main.class.getPackage().getName());
    register(JacksonFeature.class);
    register(
        new LoggingFeature(
            Logger.getLogger(DEFAULT_LOGGER_NAME), FINER, PAYLOAD_ANY, DEFAULT_MAX_ENTITY_SIZE));
    register(
        new AbstractBinder() {
          @Override
          protected void configure() {
            bind(EjbProvider.class)
                .to(new TypeLiteral<InjectionResolver<EJB>>() {})
                .in(Singleton.class);
          }
        });
  }
}
