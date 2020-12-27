package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.core.model.geo.City;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.ParticipantRole;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.PlanningMode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.Visibility;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class TravelDetails {

  private long id;
  private String tripName;
  private Collapsedpost description;
  private LocalDate startDate;
  private LocalDate endDate;
  private Visibility visibility;
  private boolean owner;
  private CurrencyCode defaultCurrency;
  private double totalCost;
  private double averageDailyCostPerPerson;
  
  private CurrencyCode budgetCurrency;
  private BigDecimal budget;
  
  private PlanningMode mode;
  
  

  private double totalCostUserCurrency;
  private double averageDailyCostPerPersonUserCurrency;
  private boolean participant;
  private ParticipantRole participantlevel;
  private int numberOfDays;
  
  private List<City> cities;
  private List<User> otherParticipants;

  private User createdBy;
  private LocalDateTime createdOn;
  private User lastModifiedBy;
  private LocalDateTime lastModifiedOn;
}
