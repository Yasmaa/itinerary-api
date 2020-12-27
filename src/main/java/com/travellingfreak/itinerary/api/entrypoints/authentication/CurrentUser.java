package com.travellingfreak.itinerary.api.entrypoints.authentication;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

  public static String getCurrentUser() {
    final KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return principal.getName();
  }
}
