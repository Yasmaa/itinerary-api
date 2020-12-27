package com.travellingfreak.itinerary.api.core.model.threads;

import com.travellingfreak.itinerary.api.core.model.accounts.User;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * User: AustPC Date: 4/16/2020 Time: 9:01 PM
 *
 * @author AustPC
 */
@Data
public class Message {
  private final long id;
  private final User createdBy;
  private final LocalDateTime createdOn;
  private final LocalDateTime lastUpdateOn;
  private final String message;
}
