package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.model.PageResponse;
import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.core.planning.AddParticipantUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteParticipantUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindFriendsToInviteUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindParticipantsUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateParticipantUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.entrypoints.requests.ParticipantRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchRoleRequest;
import com.travellingfreak.itinerary.api.entrypoints.responses.ParticipantDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.ResourceCreationResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel/{travelId}")
@Api(tags = "Participants API")
@RequiredArgsConstructor
public class ParticipantEndpoint {
  private final FindFriendsToInviteUseCase findFriendsToInviteUseCase;
  private final FindParticipantsUseCase findParticipantsUseCase;
  private final UpdateParticipantUseCase updateParticipantUseCase;
  private final AddParticipantUseCase   addParticipantUseCase ;
  private final DeleteParticipantUseCase deleteParticipantUseCase;

  @ApiOperation("Add a new participant to this plan")
  @PostMapping(value = "/participants", produces = "application/json")
  @Timed("api.participants.create")
  @PreAuthorize("hasPermission(#travelId, '', 'ADMIN')")
  public  ResourceCreationResponse addParticipants(@PathVariable("travelId") final long travelId,
                                              @RequestBody final ParticipantRequest participantRequest) {
	  return new  ResourceCreationResponse(addParticipantUseCase.addParticipant(CurrentUser.getCurrentUser(),travelId,participantRequest));
  }

  @ApiOperation("Updates the participant role. Only the owner of the trip can set someone as admin, and only the owner can remove someone with admin rights")
  @PatchMapping(value = "/participants/{id}", produces = "application/json")
  @Timed("api.participants.patch")
  @PreAuthorize("hasPermission(#travelId, '', 'ADMIN')")
  public void updateParticipantRole(@PathVariable("travelId") final long travelId,
                                              @PathVariable("id") final long participantId,
                                              @RequestBody final PatchRoleRequest patchRoleRequest) {
	  updateParticipantUseCase.updateParticipant(CurrentUser.getCurrentUser(),travelId,participantId, patchRoleRequest);
  }

  @ApiOperation("Retrieve list of possible users that matches the query and are friends with the requester.")
  @GetMapping(value = "/autocomplete/friends", produces = "application/json")
  @Timed("api.participants.get.autocomplete.friends")
  @PreAuthorize("hasPermission(#travelId, '', 'ADMIN')")
  public List<User> listParticipants(
      @PathVariable("travelId") final long travelId,
      @ApiParam(name = "name",
                value = "Might match either name or e-mail. If missing, should return all the friends for the requester")
      @RequestParam(value = "name", required = false) final String nameOrEmail) {

	  return findFriendsToInviteUseCase.listAccounts(travelId, nameOrEmail);
  }

  @ApiOperation("Retrieve list of all participants")
  @GetMapping(value = "/participants", produces = "application/json")
  @Timed("api.participants.get.autocomplete.friends")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public PageResponse<ParticipantDetails> listParticipants(@PathVariable("travelId") final long travelId,
                                                           @RequestParam final int page,
                                                           @RequestParam final int limit) {
	  return new PageResponse<>(findParticipantsUseCase.listAccounts(travelId, page, limit));
  }

  @ApiOperation(value = "Removes a participant from the specified travel. Only the owner of the travel can remove an admin user",
                code = 201
  )
  @DeleteMapping(value = "/participant/{id}", produces = "application/json")
  @Timed("api.participants.delete")
  @PreAuthorize("hasPermission(#travelId, '', 'ADMIN')")
  public void removeParticipants(@PathVariable("travelId") final long tripId,
                                                  @PathVariable("id") final long participantId){
	  deleteParticipantUseCase.deleteParticipant(tripId,participantId);
  }


}
