package com.travellingfreak.itinerary.api.entrypoints.requests;

import org.springframework.lang.NonNull;

import lombok.Data;

/**
 * Date: 7/26/2020 Time: 7:55 PM
 */
@Data
public class CreateTravelRequest {
  @NonNull private CreateTravelBody body;
  @NonNull private CreateTravelAdvancedBody advanced;
}
