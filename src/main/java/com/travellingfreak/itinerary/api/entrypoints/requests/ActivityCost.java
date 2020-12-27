package com.travellingfreak.itinerary.api.entrypoints.requests;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;

import lombok.Data;

@Data
public class ActivityCost {
  private long id;
  private String note;
  private double amount;
  private CurrencyCode currencyCode;
}
