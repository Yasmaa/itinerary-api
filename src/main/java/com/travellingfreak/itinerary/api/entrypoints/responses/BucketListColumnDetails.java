package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.util.List;

import lombok.Data;

/**
 * Date: 6/6/2020 Time: 12:08 PM
 */
@Data
public class BucketListColumnDetails {

  private long id;
  private String title;
  private List<ActivityDetails> activities;
}
