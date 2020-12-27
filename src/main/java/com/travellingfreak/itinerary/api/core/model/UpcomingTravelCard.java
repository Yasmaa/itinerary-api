package com.travellingfreak.itinerary.api.core.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * Date: 7/19/2020 Time: 11:38 PM
 */
@Data
public class UpcomingTravelCard {
  private final long id;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final int numberOfDays;
  private final List<String> participantNames;
  private final String createdByName;
}
