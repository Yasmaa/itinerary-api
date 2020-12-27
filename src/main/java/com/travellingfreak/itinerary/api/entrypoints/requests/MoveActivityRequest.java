package com.travellingfreak.itinerary.api.entrypoints.requests;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 6:23 PM
 *
 * @author AustPC
 */
@Data
public class MoveActivityRequest {
  private long sourceDay;
  private long destinationDay;
}
