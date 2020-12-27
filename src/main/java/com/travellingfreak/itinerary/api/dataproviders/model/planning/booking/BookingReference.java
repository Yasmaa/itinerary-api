package com.travellingfreak.itinerary.api.dataproviders.model.planning.booking;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 10:43 AM
 */
@EqualsAndHashCode(callSuper = true) @Entity
@Data
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor

public class BookingReference extends Audit {
  @Id
  @GeneratedValue
  private long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Travel travel;

  @ManyToOne(fetch = FetchType.LAZY)
  private AccommodationDetails details;

  @Column(name = "min_check_in_time")
  private LocalTime minCheckInTime;

  @Column(name = "max_check_out_time")
  private LocalTime maxCheckOutTime;

  @Column(name = "booked_through")
  private String bookedThrough;

  private String confirmation;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @OneToOne
  private Cost cost;
  
  
  /*
  @OneToMany
  private List<LodgingActivity> linkedActivities;

  

  public void addActivitiesTo() {
    travel.findDay(startDate).ifPresent(it -> {
      final LodgingActivity checkIn = createCheckIn();
      it.addActivity(checkIn);
      linkedActivities.add(checkIn);
    });

    travel.findDay(endDate).ifPresent(it -> {
      final LodgingActivity checkOut = createCheckOut();
      it.addActivity(checkOut);
      linkedActivities.add(checkOut);
    });
  }

  private LodgingActivity createCheckOut() {
    final var checkOut = new LodgingActivity();
    checkOut.setBookingRef(this);
    checkOut.setCategory(Category.LODGING);
    //checkOut.setSubcategory(SubCategory.CHECK_OUT);
    checkOut.setEventTime(maxCheckOutTime);
    checkOut.setTitle("Check-out from " + details.getName());
    return checkOut;
  }

  private LodgingActivity createCheckIn() {
    final var checkIn = new LodgingActivity();
    checkIn.setBookingRef(this);
    checkIn.setCategory(Category.LODGING);
    //checkIn.setSubcategory(SubCategory.CHECK_IN);
    checkIn.setEventTime(minCheckInTime);
    checkIn.setTitle("Check-in at " + details.getName());
    return checkIn;
  }

  public void addCost(final Cost c) {
    c.setTravel(travel);
    c.setParentBooking(this);
    this.cost = c;
  }

  public void clearCost() {
    this.cost.setTravel(null);
    this.cost.setParentBooking(null);
    this.cost = null;
  }
*/
}
