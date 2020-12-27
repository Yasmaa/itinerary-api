package com.travellingfreak.itinerary.api.entrypoints.requests;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.Category;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.SubCategory;

import java.time.LocalTime;

import lombok.Data;

@Data
public class CreateActivityForm {

  private String gate;	
  private Category category;
  private SubCategory subCategory;

  private String title;
  private String description;

  private LocalTime startTime;
  private LocalTime endTime;

  private LocalTime departureTime;
  private Integer flightDurationInMinutes;
  private long startLocation;
  private long endLocation;
  private String flightNumber;

  
  private double price;
  
  private Integer travelDurationInSeconds;
  private Integer distanceCovered;
  private long pass;
}
