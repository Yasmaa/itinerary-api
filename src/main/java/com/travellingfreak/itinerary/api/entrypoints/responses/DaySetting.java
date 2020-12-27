package com.travellingfreak.itinerary.api.entrypoints.responses;


import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.MeasurementUnit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.TransitMode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.DayClassification;

import java.math.BigDecimal;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 8:41 PM
 *
 * @author AustPC
 */
@Data
@AllArgsConstructor
public class DaySetting {
  private  TravelSettings travelSettings;

  private CurrencyCode defaultCurrency;
  private  MeasurementUnit distanceUnit;
  private  int maximumActivitiesPerDay;
  private  DayClassification planningType;
  private TransitMode defaultTransportMode;

  private  LocalTime dayStartsAt;
  private  LocalTime dayEndsAt;
  private  BigDecimal dailyBudget;


}
