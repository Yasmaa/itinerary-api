package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 6:35 PM
 *
 * @author AustPC
 */
@Data
public class DayOverviewDetails {
  private List<BookingQuickView> hotel;
  private LocalTime minPlannableTime;
  private LocalTime maxPlannableTime;

  private  List<CalendarActivity> allDayActivities;
  private  List<CalendarActivity> plannedActivities;
  private  double estimatedCost;
  private  CurrencyCode currency;
  private  int alreadyPlannedHours;
  private  int totalPlanHours;

  private User createdBy;
  private LocalDateTime createdOn;
  private User lastModifiedBy;
  private LocalDateTime lastModifiedOn;
//  private final List<ActivitySuggestion> activitySuggestions;

}
