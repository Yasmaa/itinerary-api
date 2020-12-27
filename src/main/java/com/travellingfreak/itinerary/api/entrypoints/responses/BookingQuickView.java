package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingQuickView {
  private long id;
  private String accommodationName;
  private LocalDate startDate;
  private LocalDate endDate;
}
