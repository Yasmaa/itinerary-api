package com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.DayClassification;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.MeasurementUnit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.TransitMode;
import java.math.BigDecimal;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 8/5/2020 Time: 6:06 PM
 */
@Entity
@Table(name = "day_settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaySettings extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "default_currency")
  private CurrencyCode defaultCurrency;
  @Column(name = "distance_unit")
  private MeasurementUnit distanceUnit;
  @Column(name = "maximum_activities_per_day")
  private int maximumActivitiesPerDay;
  @Column(name = "planning_type")
  private DayClassification planningType;
  @Column(name = "default_transport_mode")
  private TransitMode defaultTransportMode;

  @Column(name = "day_starts_at")
  private LocalTime dayStartsAt;
  @Column(name = "day_ends_at")
  private LocalTime dayEndsAt;
  @Column(name = "daily_budget")
  private BigDecimal dailyBudget;
  
  @OneToOne
  private Day day;
}
