package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.model.PageResponse;
import com.travellingfreak.itinerary.api.core.model.UpcomingTravelCard;
import com.travellingfreak.itinerary.api.core.planning.CreatePlanningSlotUseCase;
import com.travellingfreak.itinerary.api.core.planning.CreateTravelUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeletePlanningSlotUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindActiveTravelsUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindArchivedTravelsUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindTravelDetailsUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.core.planning.MarkTravelArchivedUseCase;
import com.travellingfreak.itinerary.api.core.planning.MoveDayUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdatePlanningSlotUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateTravelBasicData;
import com.travellingfreak.itinerary.api.core.planning.UpdateTravelSettingsUseCase;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateDayRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateTravelRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.MoveDayRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchDayRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchTravelRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchTravelSettingsRequest;
import com.travellingfreak.itinerary.api.entrypoints.responses.CreateDayResponse;
import com.travellingfreak.itinerary.api.entrypoints.responses.CreateTravelResponse;
import com.travellingfreak.itinerary.api.entrypoints.responses.TravelDetails;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * Date: 7/19/2020 Time: 9:03 PM
 */
@RestController
@RequestMapping("/api/travel")
@Api(tags = "Travel Entity API")
@RequiredArgsConstructor
public class TravelEndpoint {

  private final FindActiveTravelsUseCase findActiveTravelsUseCase;
  private final FindArchivedTravelsUseCase findArchivedTravelsUseCase;  	
  private final CreateTravelUseCase createTravelUseCase;
  private final MarkTravelArchivedUseCase markTravelArchived;
  private final FindTravelDetailsUseCase findTravelDetailsUseCase;
  private final CreatePlanningSlotUseCase createPlanningSlotUseCase;
  private final UpdatePlanningSlotUseCase updatePlanningSlotUseCase;
  private final MoveDayUseCase moveDayUseCase;
  private final DeletePlanningSlotUseCase deletePlanningSlotUseCase;
  private final UpdateTravelSettingsUseCase updateTravelSettingsUseCase;
  private final UpdateTravelBasicData updateTravelBasicData;
  
  @ApiOperation(value = "Get all travels for the currently authenticated user",
                notes = "Allows to retrieve all active travel plans in which the current user is a participant."
                        + "The list is returned sorted by creation date"
  )
  @GetMapping(value = "/upcoming", produces = "application/json")
  @Timed("api.travel.get.upcoming")
  @PreAuthorize("hasRole('USER')")
  public PageResponse<UpcomingTravelCard> findUpcomingTravels(
      @RequestParam @Positive final int page,
      @RequestParam @Positive final int limit) {
	    return new PageResponse<>(findActiveTravelsUseCase.findUpcomingTravels(CurrentUser.getCurrentUser(), page, limit));
  }

  @GetMapping(value = "/archived", produces = "application/json")
  @Timed("api.travel.get.archived")
  @PreAuthorize("hasRole('USER')")
  public PageResponse<UpcomingTravelCard> findArchivedTravels(
      @RequestParam @Positive final int page,
      @RequestParam @Positive final int limit) {
	    return new PageResponse<>(findArchivedTravelsUseCase.findArchivedTravels(CurrentUser.getCurrentUser(), page, limit));
  }

  
  @PostMapping(value = "/create", produces = "application/json")
  @Timed("api.travel.post.create")
  @PreAuthorize("hasRole('USER')")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateTravelResponse createNewTravel(@RequestBody final @Valid CreateTravelRequest request) {
    
    return new CreateTravelResponse(createTravelUseCase.createTravelUseCase(CurrentUser.getCurrentUser(), request));
  }

  @ApiOperation(value = "Get details for a specific travel",
                notes = "Allows to retrieve all travel plans in which the current user is a participant. The list is returned sorted by creation date"
  )
  @GetMapping(value = "{id}", produces = "application/json")
  @Timed("api.travel.get.details")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public TravelDetails getTravelDetails(
                                        @PathVariable("id") final long travelId) {
	  return findTravelDetailsUseCase.findTravelDetails(CurrentUser.getCurrentUser(),travelId);
  }

  @ApiOperation(value = "Mark a travel as archived")
  @DeleteMapping(value = "{id}", produces = "application/json")
  @Timed("api.travel.delete")
  @PreAuthorize("hasPermission(#travelId, '', 'ADMIN')")
  public void deleteTravel( @PathVariable("id") final long travelId) {
    markTravelArchived.markTravelArchived(travelId);
  }

  @ApiOperation(value = "Creates new planning slot",
                notes = "Allows authenticated users to create a new planning slot"
  )
  @PostMapping(value = "{id}/day", produces = "application/json")
  @Timed("api.planning.createDay")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public CreateDayResponse createDay(
                                     @PathVariable("id") final long travelId,
                                     @RequestBody final CreateDayRequest createDayRequest) {

	  return new CreateDayResponse(createPlanningSlotUseCase.addPlanningSlot(CurrentUser.getCurrentUser(),travelId, createDayRequest));
  }

  @ApiOperation("Modifies an existing planning slot")
  @PatchMapping(value = "{id}/day/{dayId}", produces = "application/json")
  @Timed("api.planning.archiveDay")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void patchDay(
                       @PathVariable("id") final long travelId,
                       @PathVariable("dayId") final long dayId,
                       @RequestBody final PatchDayRequest patchDayRequest){
	  updatePlanningSlotUseCase.modifyPlanningSlot(CurrentUser.getCurrentUser(),travelId, dayId, patchDayRequest);
  }

  @ApiOperation("Move a day and all associated activities and discussion into the selected slot. You can specify either after or before the destination id "
                + "not both")
  @PatchMapping(value = "{id}/day/{dayId}/position", produces = "application/json")
  @Timed("api.planning.archiveDay")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void moveDay(
                      @PathVariable("id") final long travelId,
                      @PathVariable("dayId") final long dayId,
                      @RequestBody final MoveDayRequest moveDayRequest
  ){
	  moveDayUseCase.moveDay(travelId, dayId, moveDayRequest);
  }

  @ApiOperation("Deletes a planning slot with the option to transfer all the planned activities back to the bucket list")
  @DeleteMapping(value = "{id}/day/{dayId}", produces = "application/json")
  @Timed("api.planning.archiveDay")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void deleteDay(
                         @PathVariable("id") final long travelId,
                         @PathVariable("dayId") final long dayId,
                         @RequestParam("deleteAllActivities") final boolean deleteAllActivities){
	  deletePlanningSlotUseCase.deleteDay(travelId, dayId,deleteAllActivities);
  }



  @ApiOperation(
      value = "Updates the basic data of a given travel",
      notes = "Allows authenticated user and editor of the travel entity to update its description or title"
  )
  @PatchMapping(value = "{id}", produces = "application/json")
  @PreAuthorize("hasPermission(#tripId, '', 'WRITE')")
  @Timed("api.travel.update")
  public void update(@ApiParam(value = "Travel id to update",
                               example = "123456")
                     @PathVariable("id") final long tripId,
                     @RequestBody final @Valid PatchTravelRequest request) {
	  updateTravelBasicData.updateTravelBasicData(CurrentUser.getCurrentUser(), tripId, request);
  }

  @ApiOperation(
      value = "Updates the settings data of a given travel",
      notes = "Allows authenticated user and editor of the travel entity to update its description or title"
  )
  @PatchMapping(value = "{id}/settings", produces = "application/json")
  @PreAuthorize("hasPermission(#tripId, '', 'WRITE')")
  @Timed("api.travel.update")
  public void updateSettings(@ApiParam(value = "Travel id to update",
                               example = "123456")
                     @PathVariable("id") final long tripId,
                     @RequestBody final @Valid PatchTravelSettingsRequest request) {
	  updateTravelSettingsUseCase.updateTravelSettings(tripId, request);
  }

}
