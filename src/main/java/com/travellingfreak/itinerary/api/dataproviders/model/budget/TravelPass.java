package com.travellingfreak.itinerary.api.dataproviders.model.budget;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/18/2020 Time: 10:23 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity(name = "TravelPass")
@Table(name = "travel_passes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelPass extends Audit {
  @Id
  @GeneratedValue
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Travel travel;

  @ManyToOne(fetch = FetchType.LAZY)
  private Pass pass;

  @ManyToOne(fetch = FetchType.LAZY)
  private BuyOption selectedOption;

}
