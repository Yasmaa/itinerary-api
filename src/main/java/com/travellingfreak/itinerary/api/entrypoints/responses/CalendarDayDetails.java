package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.time.LocalTime;
import java.util.List;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 5:02 PM
 *
 * @author AustPC
 */
@Data
public class CalendarDayDetails {
  private  long dayId;
  private  int dayNumber;
  // activities are planned before start time or after end time
  private  boolean isOverbooked;

  private  int dayOfMonth;
  private  LocalTime startTime;
  private  LocalTime endTime;

  // activities without start time
  private  List<CalendarActivity> allDaysActivities;

  // activities with start time / end time sorted by start time
  private  List<CalendarActivity> plannedActivities;

}
