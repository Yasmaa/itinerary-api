package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 6:19 PM
 *
 * @author AustPC
 */
@Data
public class CalendarWeekView {
  private  int month;
  private  int year;
  private  LocalDate startDate;
  private  LocalDate endDate;

  private  List<CalendarDayDetails> calendarDays;
}
