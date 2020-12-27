package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 5:13 PM
 *
 * @author AustPC
 */
@Data
public class CalendarActivity implements Comparable<CalendarActivity> {
  private  long id;
  private  String title;
  private  LocalTime startTime;
  private  LocalTime endTime;

  private int likeCount;
  private int dislikeCount;
  private boolean currentUserLikes;
  private boolean currentUserDislikes;
  private User createdBy;
  private LocalDateTime createdOn;
  private User lastModifiedBy;
  private LocalDateTime lastModifiedOn;

  @Override public int compareTo(CalendarActivity o) {
    return startTime.compareTo(o.startTime);
  }
}
