package com.travellingfreak.itinerary.api.entrypoints.requests;

import java.time.LocalTime;

import lombok.Data;

@Data
public class PatchAccommodationRequest {
  private String name;
  private String country;
  private String city;
  private String address;
  private String streetAddress;
  private String postCode;
  private LocalTime minCheckIn;
  private LocalTime maxCheckOut;
  private int numberOfStars;
}
