package se.ifmo.soa.lab2.services.secondary;

import static java.util.logging.Level.FINER;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_LOGGER_NAME;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_MAX_ENTITY_SIZE;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.PAYLOAD_ANY;

import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

@ApplicationPath("")
public class Main extends ResourceConfig {

  public Main() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    packages(Main.class.getPackage().getName());
    register(JacksonFeature.class);
    register(
        new LoggingFeature(
            Logger.getLogger(DEFAULT_LOGGER_NAME), FINER, PAYLOAD_ANY, DEFAULT_MAX_ENTITY_SIZE));
  }
}
