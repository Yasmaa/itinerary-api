package com.travellingfreak.itinerary.api.core.model.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: AustPC Date: 3/17/2020 Time: 11:00 PM
 *
 * @author AustPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private String id;
  
  private String name;
  private String email;
}
