package com.travellingfreak.itinerary.api.dataproviders.model.planning;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 10:18 AM
 */
@Entity
@Table(name = "locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

  @Id
  @GeneratedValue
  private long id;
  private String name;
  private String address;
  private double latitude;
  private double longitude;
  //private Day day;
}
