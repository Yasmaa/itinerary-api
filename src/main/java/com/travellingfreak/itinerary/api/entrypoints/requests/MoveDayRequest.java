package com.travellingfreak.itinerary.api.entrypoints.requests;

import lombok.Data;

/**
 * User: AustPC Date: 4/17/2020 Time: 1:48 PM
 *
 * @author AustPC
 */
@Data
public class MoveDayRequest {
  
  private long afterDayId;
  private long beforeDayId;

}
