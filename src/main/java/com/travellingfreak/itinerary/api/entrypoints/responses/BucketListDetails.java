package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.util.List;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 6:45 PM
 *
 * @author AustPC
 */
@Data
public class BucketListDetails {
  private  List<BucketListColumnDetails> columns;
}
