package com.travellingfreak.itinerary.api.entrypoints.requests;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.DayClassification;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.MeasurementUnit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.TemperatureUnit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.TransitMode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.Visibility;

import java.math.BigDecimal;
import java.time.LocalTime;

import lombok.Data;

@Data
public class PatchTravelSettingsRequest {
  private CurrencyCode defaultCurrency;
  private MeasurementUnit distanceUnit;
  private TemperatureUnit temperatureMeasureUnit;
  private Integer maximumActivitiesPerDay;
  private DayClassification  planningType;
  private TransitMode defaultTransportMode;
  private Boolean allowGuestsToInvite;
  private Boolean allowFileSharing;
  private Boolean allowDiscussions;
  private Boolean allowEmailProcessing;
  private LocalTime dayStartsAt;
  private LocalTime dayEndsAt;
  private BigDecimal dailyBudget;
  private BigDecimal budget;
  private CurrencyCode budgetCurrency;
  private Visibility visibility;
}
