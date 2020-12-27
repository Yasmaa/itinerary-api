package com.travellingfreak.itinerary.api.entrypoints.requests;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.ParticipantRole;

import lombok.Data;

@Data
public class PatchRoleRequest {
  private ParticipantRole role;
}
