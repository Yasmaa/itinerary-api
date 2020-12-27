package com.travellingfreak.itinerary.api.dataproviders.model.accounts;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.MeasurementUnit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.TemperatureUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 2:25 PM
 */
@Entity
@Table(name = "account_settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSetting {

  @Id
  @GeneratedValue
  private long id;

  @Column(name = "date_format")
  private String dateFormat;

  @Column(name = "default_currency")
  @Enumerated(EnumType.STRING)
  private CurrencyCode defaultCurrency;

  @Column(name = "monetary_number_format")
  private String monetaryNumberFormat;

  @Column(name = "time_format")
  private String timeFormat;

  @Column(name = "temperature_measure_unit")
  @Enumerated(EnumType.STRING)
  private TemperatureUnit temperatureMeasureUnit;

  @Column(name = "distance_measure_unit")
  @Enumerated(EnumType.STRING)
  private MeasurementUnit distanceMeasureUnit;
}
