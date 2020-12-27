package com.travellingfreak.itinerary.api.core.model.threads;

import com.travellingfreak.itinerary.api.core.model.accounts.User;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * User: AustPC Date: 4/16/2020 Time: 9:00 PM
 *
 * @author AustPC
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CollapsedPost extends Message {
  private final Message lastReply;
  private final long replyCount;

  public CollapsedPost(final long id,
                       final User createdBy,
                       final LocalDateTime createdOn,
                       final LocalDateTime lastUpdateOn,
                       final String message,
                       final Message lastReply,
                       final long replyCount) {
    super(id, createdBy, createdOn, lastUpdateOn, message);
    this.lastReply = lastReply;
    this.replyCount = replyCount;
  }
}


