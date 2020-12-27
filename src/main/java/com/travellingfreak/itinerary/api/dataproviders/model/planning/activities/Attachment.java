package com.travellingfreak.itinerary.api.dataproviders.model.planning.activities;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 12:36 PM
 */
@EqualsAndHashCode(callSuper = true) @Entity
@Table(name = "attachments")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Attachment extends Audit {
  @Id
  @GeneratedValue
  private long id;
  @ManyToOne
  private Activity activity;

  private String caption;
  private String url;

  @Column(name = "local_file")
  private String localFile;

}
