package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.Category;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * User: AustPC Date: 4/16/2020 Time: 8:57 PM
 *
 * @author AustPC
 */
@Data
public class GeoTaggedActivity {
  private LatLng location;
  private Category activityCategory;
  private String title;

  private int likeCount;
  private int dislikeCount;
  private boolean currentUserLikes;
  private boolean currentUserDislikes;
  private User createdBy;
  private LocalDateTime createdOn;
  private User lastModifiedBy;
  private LocalDateTime lastModifiedOn;

}
