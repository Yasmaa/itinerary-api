package com.travellingfreak.itinerary.api.entrypoints.requests;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.PlanningMode;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatchTravelRequest {
  private String title;
  private String description;
  private PlanningMode mode;
  private String coverPhoto;
  private LocalDate startDate;
  private LocalDate endDate;
  private int numberOfDays;
}
