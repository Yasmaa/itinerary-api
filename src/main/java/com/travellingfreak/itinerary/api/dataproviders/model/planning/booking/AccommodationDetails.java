package com.travellingfreak.itinerary.api.dataproviders.model.planning.booking;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Location;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 11:39 AM
 */
@Entity
@Table(name = "accommodations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDetails extends Audit {

  @Id
  @GeneratedValue
  private long id;
  private boolean published;
  private boolean isCustom;

  @ManyToOne(fetch = FetchType.LAZY)
  private Location location;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Travel travel;

  private String name;
  private String country;
  private String city;
  private String address;
  @Column(name = "street_address")
  private String streetAddress;
  @Column(name = "post_code")
  private String postCode;
  @Column(name = "web_rating")
  private String webRating;
  @Column(name = "min_check_in")
  private LocalTime minCheckIn;
  @Column(name = "max_check_out")
  private LocalTime maxCheckOut;
  @Column(name = "accepted_payment")
  private String acceptedPayment;
  private int numberOfStars;
  private String source;
  
}
