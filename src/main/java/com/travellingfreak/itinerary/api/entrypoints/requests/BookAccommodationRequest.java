package com.travellingfreak.itinerary.api.entrypoints.requests;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class BookAccommodationRequest {
  private long accommodationId;
  private LocalDate startDate;
  private LocalDate endDate;
  private double amount;
  private CurrencyCode currencyCode;

  private LocalTime minCheckInTime;
  private LocalTime maxCheckOutTime;
  private String bookedThrough;
  private String confirmation;


}
