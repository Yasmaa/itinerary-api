package com.travellingfreak.itinerary.api.dataproviders.model.accounts;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.ParticipantRole;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 3:27 PM
 */
@Table(name = "participants")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Participant extends Audit {
  @Id
  @GeneratedValue
  private long id;

  @Enumerated(EnumType.STRING)
  private ParticipantRole role;

  @ManyToOne
  private Travel travel;

  private String accountId;
}
