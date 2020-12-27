package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.ParticipantRole;

import lombok.Data;

/**
 * Date: 8/12/2020 Time: 2:21 PM
 */
@Data
public class ParticipantDetails {
  private final long id;
  private final User user;
  private final ParticipantRole role;
}
