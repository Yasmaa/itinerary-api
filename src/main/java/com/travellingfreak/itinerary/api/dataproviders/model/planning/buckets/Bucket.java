package com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets;

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
 * Date: 7/19/2020 Time: 10:06 AM
 */
@EqualsAndHashCode(callSuper = true) @Entity(name = "Bucket")
@Table(name = "buckets")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Bucket extends Audit {

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Travel travel;

  private String name;

  

}
