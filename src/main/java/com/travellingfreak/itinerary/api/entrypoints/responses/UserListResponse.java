package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.core.model.accounts.User;

import java.util.List;

import lombok.Data;

@Data
public class UserListResponse {
  private List<User> users;
}
