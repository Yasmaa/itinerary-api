package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.util.List;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 8:42 PM
 *
 * @author AustPC
 */
@Data
public class DayMapView {
  private LatLngBounds mapBoundaries;
  private List<GeoTaggedActivity> activities;

}
