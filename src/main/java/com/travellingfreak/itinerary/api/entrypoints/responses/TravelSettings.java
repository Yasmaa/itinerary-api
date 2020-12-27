package com.travellingfreak.itinerary.api.entrypoints.responses;



import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.MeasurementUnit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.TransitMode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.Visibility;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.DayClassification;


import java.math.BigDecimal;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 8:55 PM
 *
 * @author AustPC
 */
@Data
@AllArgsConstructor
public class TravelSettings {
  private  CurrencyCode defaultCurrency;
  private  MeasurementUnit distanceUnit;
  private  int maximumActivitiesPerDay;
  private  DayClassification planningType;
  private  TransitMode defaultTransportMode;
  private  Visibility visibility;

  private  boolean allowGuestsToInvite;
  private  boolean allowFileSharing;
  private  boolean allowDiscussions;
  private  boolean allowEmailProcessing;

  private  LocalTime dayStartsAt;
  private  LocalTime dayEndsAt;
  private  BigDecimal dailyBudget;
}
