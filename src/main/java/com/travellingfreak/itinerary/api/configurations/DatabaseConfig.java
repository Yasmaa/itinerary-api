package com.travellingfreak.itinerary.api.configurations;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import java.util.Properties;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/19/2020 Time: 8:35 PM
 */
@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories
public class DatabaseConfig {
  private final DataSource dataSource;
  @Bean
  public LocalSessionFactoryBuilder sessionFactoryBuilder() {
    LocalSessionFactoryBuilder sfb = new LocalSessionFactoryBuilder(dataSource);
    sfb.scanPackages("com.travellingfreak.itinerary.api");
    // Hibernate/JPA properties
    Properties properties = new Properties();
    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
    properties.put("hibernate.hbm2ddl.auto", "update");
    sfb.addProperties(properties);
    return sfb;
  }

  @Bean
  public SessionFactory entityManagerFactory () {
    return sessionFactoryBuilder().buildSessionFactory();
  }

}
