package com.travellingfreak.itinerary.api.entrypoints.responses;

import lombok.Data;

/**
 * User: AustPC Date: 4/4/2020 Time: 11:25 AM
 *
 * @author AustPC
 */
@Data
public class LatLngBounds {
  private  LatLng topLeft;
  private  LatLng bottomRight;
}
