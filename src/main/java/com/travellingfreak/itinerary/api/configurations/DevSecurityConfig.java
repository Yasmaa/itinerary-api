package com.travellingfreak.itinerary.api.configurations;

import org.keycloak.KeycloakPrincipal;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

/**
 * Date: 7/19/2020 Time: 9:53 PM
 */
@EnableWebSecurity
@Profile("develop")
public class DevSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable().addFilterBefore((servletRequest, servletResponse, filterChain) -> {
                           SecurityContextHolder.getContext()
                               .setAuthentication(
                                   new UsernamePasswordAuthenticationToken(
                                       new KeycloakPrincipal<>("user.test", null), List.of(
                                       new SimpleGrantedAuthority("USER")
                                   )
                                   )
                               );
                           filterChain.doFilter(servletRequest, servletResponse);
                         }
    , BasicAuthenticationFilter.class);
  }
}
