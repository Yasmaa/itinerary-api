package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.Category;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.SubCategory;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ActivityDetails {


  private long id;
  private Category category;
  private SubCategory subCategory;

  private String title;
  private Collapsedpost description;

  private LocalTime startTime;
  private LocalTime endTime;

  private Integer flightDurationInMinutes;
  private long startLocation;
  private long endLocation;
  private String flightNumber;

  private String gate;
  private double price;

  private Integer travelDurationInSeconds;
  private Integer distanceCovered;
  private long pass;

  private int likeCount;
  private int dislikeCount;
  private boolean currentUserLikes;
  private boolean currentUserDislikes;
  private User createdBy;
  private LocalDateTime createdOn;
  private User lastModifiedBy;
  private LocalDateTime lastModifiedOn;

}
