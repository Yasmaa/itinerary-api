package com.travellingfreak.itinerary.api.dataproviders.model.base;

import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * Date: 7/19/2020 Time: 9:32 AM
 */
@MappedSuperclass
@Data
public class SoftDelete {
  private boolean deleted;
}
