package com.travellingfreak.itinerary.api.configurations;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtraKeycloakThing {
  @Bean
  public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

}
