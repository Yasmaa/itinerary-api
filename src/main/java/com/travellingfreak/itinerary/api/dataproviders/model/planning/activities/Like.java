package com.travellingfreak.itinerary.api.dataproviders.model.planning.activities;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;

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
 * Date: 7/19/2020 Time: 12:36 PM
 */
@Entity
@Table(name = "activity_likes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Like extends Audit {
  @Id
  @GeneratedValue
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Activity activity;

  @Column(name = "thumbs_up")
  private boolean thumbsUp;

}
