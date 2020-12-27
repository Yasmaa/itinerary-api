package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.model.PageResponse;
import com.travellingfreak.itinerary.api.core.planning.AddNewActivityToADayUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindDaySettingsUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindPlanningslotDiscussionsUseCase;
import com.travellingfreak.itinerary.api.core.planning.GetPlanningSlotAsMapUseCase;
import com.travellingfreak.itinerary.api.core.planning.GetPlanningSlotUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateActivityForm;
import com.travellingfreak.itinerary.api.entrypoints.responses.Collapsedpost;
import com.travellingfreak.itinerary.api.entrypoints.responses.DayBudgetView;
import com.travellingfreak.itinerary.api.entrypoints.responses.DayMapView;
import com.travellingfreak.itinerary.api.entrypoints.responses.DayOverviewDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.DaySetting;
import com.travellingfreak.itinerary.api.entrypoints.responses.ResourceCreationResponse;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/planning/{travelId}/{day}")
@Api(tags = "Planning API")
@RequiredArgsConstructor
public class PlanningEndpoint {
	
  private final AddNewActivityToADayUseCase addNewActivityToDayUseCase;
  private final FindPlanningslotDiscussionsUseCase findPlanningslotDiscussions;
  private final FindDaySettingsUseCase findDaySettings;
  private final GetPlanningSlotAsMapUseCase getPlanningSlotAsMapUseCase;
  private final GetPlanningSlotUseCase getPlanningSlotUseCase;
  
  @ApiOperation("View planning slot (which is the day)")
  @GetMapping(value = "overview", produces = "application/json")
  @Timed("api.planning.view.planning.overview")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public DayOverviewDetails getOverview(@PathVariable("travelId") final long travelId,
                                        @PathVariable("day") final long dayId) {
    return getPlanningSlotUseCase.getPlanningSlot(CurrentUser.getCurrentUser(),travelId, dayId);
  }

  @ApiOperation("View planning slot as budget list")
  @GetMapping(value = "budget", produces = "application/json")
  @Timed("api.planning.view.planning.budget")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public DayBudgetView getBudgetView(@PathVariable("travelId") final long travelId,
                                     @ApiIgnore @AuthenticationPrincipal final KeycloakPrincipal userDetails,
                                     @PathVariable("day") final long id) {
    return null;
  }

  @ApiOperation(value = "View settings.", notes = "It also returns the settings for the travel, which are applied if no settings are specified for the day")
  @GetMapping(value = "settings", produces = "application/json")
  @Timed("api.planning.view.planning.budget")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public DaySetting getSettingsView(@PathVariable("travelId") final long travelId,
                                     @PathVariable("day") final long dayId) {
    return findDaySettings.findDaySettings(travelId, dayId);
  }

  @ApiOperation("View planning slot as map")
  @GetMapping(value = "map", produces = "application/json")
  @Timed("api.planning.view.planning.budget")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public DayMapView getMapView(@PathVariable("travelId") final long travelId,
                               @PathVariable("day") final long dayId) {
    return getPlanningSlotAsMapUseCase.getPlanningSlotAsMap(travelId, dayId);
  }

  @ApiOperation(value = "View planning discussions.", notes = "It should also mark all the recent posts as read for the current user (after returning the list)")
  @GetMapping(value = "discussion", produces = "application/json")
  @Timed("api.planning.view.planning.budget")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public  PageResponse<Collapsedpost> getDiscussionView(@PathVariable("travelId") final long travelId,
                                                       @PathVariable("day") final long id,
                                                       @RequestParam @Positive final int page,
                                                       @RequestParam @Positive final int limit) {
    return new  PageResponse<>(findPlanningslotDiscussions.planningDiscussions(travelId,id,page,limit));
  }

  @ApiOperation("Creates an activity and add it to the day")
  @PostMapping(value = "", produces = "application/json")
  @Timed("api.activity.add")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public ResourceCreationResponse addItem(@PathVariable("travelId") final long travelId,
                                                @PathVariable("day") final long id,
                                                @RequestBody final CreateActivityForm createActivityForm) {
	  return new ResourceCreationResponse(addNewActivityToDayUseCase.addActivitytoAday(CurrentUser.getCurrentUser(),travelId, id, createActivityForm));
  }


}
