package com.travellingfreak.itinerary.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.travellingfreak.itinerary.api")
@EnableJpaRepositories
public class ItineraryApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItineraryApiApplication.class, args);

  }

}
