package se.ifmo.soa.lab2.configurations;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.ifmo.soa.lab2.repositories.ExtendedJpaRepository;
import se.ifmo.soa.lab2.repositories.ExtendedJpaRepositoryImpl;

@Configuration
@EntityScan("se.ifmo.soa.domain")
@EnableJpaRepositories(
    basePackageClasses = ExtendedJpaRepository.class,
    repositoryBaseClass = ExtendedJpaRepositoryImpl.class)
public class JpaConfiguration {}
