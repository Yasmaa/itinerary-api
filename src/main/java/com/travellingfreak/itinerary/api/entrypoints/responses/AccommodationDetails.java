package com.travellingfreak.itinerary.api.entrypoints.responses;

import lombok.Data;

@Data
public class AccommodationDetails {

  private long id;
  private String name;
  private String country;
  private String city;
  private String address;
  private String streetAddress;
  private String postCode;
  private String webRating;
  private String minCheckIn;
  private String maxCheckOut;
  private String acceptedPayment;
  private int numberOfStars;
  private String source;
  private boolean isCustom;
}
