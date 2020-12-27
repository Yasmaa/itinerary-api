package com.travellingfreak.itinerary.api.entrypoints.requests;

import lombok.Data;

/**
 * User: AustPC Date: 4/17/2020 Time: 2:09 PM
 *
 * @author AustPC
 */
@Data
public class PatchDayRequest {
  private String title;
  private String notes;
}
