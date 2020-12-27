package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;

import java.util.List;

import lombok.Data;

/**
 * Date: 8/9/2020 Time: 12:03 PM
 */
@Data
public class ActivityReaction {
  private List<User> likes;
  private List<User> dislikes;
}
