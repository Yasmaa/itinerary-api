package com.travellingfreak.itinerary.api.entrypoints.requests;

import java.time.LocalTime;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 6:23 PM
 *
 * @author AustPC
 */
@Data
public class PatchActivityTimeRequest {
  private LocalTime start;
  private LocalTime end;
}
