package com.travellingfreak.itinerary.api.entrypoints.requests;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;

import lombok.Data;

@Data
public class AddCostRequest {
  private double price;
  private CurrencyCode currencyCode;
  private String note;
}
