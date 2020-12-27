package com.travellingfreak.itinerary.api.core.model.geo;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Location;

import lombok.Data;

@Data
public class Place {
  private Location location;
  private String tag;
  
  
 
}
