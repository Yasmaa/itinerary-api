package com.travellingfreak.itinerary.api.core.model;

import lombok.Getter;

/**
 * Date: 7/26/2020 Time: 9:14 PM
 */
@Getter
public class Counter {
  private int count;
  public void inc() {
    count++;
  }
}
