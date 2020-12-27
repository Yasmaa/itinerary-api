package com.travellingfreak.itinerary.api.entrypoints.responses;


import com.travellingfreak.itinerary.api.core.model.threads.CollapsedPost;

import java.util.List;

import lombok.Data;

/**
 * User: AustPC Date: 4/15/2020 Time: 8:42 PM
 *
 * @author AustPC
 */
@Data
public class DayDiscussionView {
  private final List<CollapsedPost> posts;
}
