package se.ifmo.soa.lab3.services.main.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaServer
@EnableCircuitBreaker
@EnableZuulProxy
@EnableConfigServer
public class Main {

  public static void main(final String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
