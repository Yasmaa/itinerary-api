package com.travellingfreak.itinerary.api.core.model.threads;

import com.travellingfreak.itinerary.api.core.model.accounts.User;

import java.time.LocalDateTime;
import java.util.List;

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
public class Post extends Message {
  private final List<Message> replies;

  public Post(final long id,
              final User createdBy,
              final LocalDateTime createdOn,
              final LocalDateTime lastUpdateOn,
              final String message,
              final List<Message> replies) {
    super(id, createdBy, createdOn, lastUpdateOn, message);
    this.replies = replies;
  }
}


