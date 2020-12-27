package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.model.PageResponse;
import com.travellingfreak.itinerary.api.core.planning.AddActivityCostUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteActivityCostUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteActivityUseCase;
import com.travellingfreak.itinerary.api.core.planning.DislikeActivityUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindActivityCostsUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindActivityDetailsUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindActivityReactionsUseCase;
import com.travellingfreak.itinerary.api.core.planning.GetActivityCostsUseCase;
import com.travellingfreak.itinerary.api.core.planning.LikeActivityUseCase;
import com.travellingfreak.itinerary.api.core.planning.MoveActivityUseCase;
import com.travellingfreak.itinerary.api.core.planning.RemoveReactionActivityUseCase;
import com.travellingfreak.itinerary.api.core.planning.RenderCalendarMonthViewAtTravelStartUseCase;
import com.travellingfreak.itinerary.api.core.planning.RenderCalendarMonthViewUseCase;
import com.travellingfreak.itinerary.api.core.planning.RenderCalendarWeekViewUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateActivityCostUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateActivityTimeUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateActivityUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.entrypoints.requests.ActivityCost;
import com.travellingfreak.itinerary.api.entrypoints.requests.AddCostRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateActivityForm;
import com.travellingfreak.itinerary.api.entrypoints.requests.MoveActivityRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchActivityTimeRequest;
import com.travellingfreak.itinerary.api.entrypoints.responses.ActivityDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.ActivityReaction;
import com.travellingfreak.itinerary.api.entrypoints.responses.CalendarMonthView;
import com.travellingfreak.itinerary.api.entrypoints.responses.CalendarWeekView;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/activities/{travelId}")
@Api(tags = "Activities API")
@RequiredArgsConstructor
public class ActivitiesEndpoint {

  private final GetActivityCostsUseCase costs;
  private final RenderCalendarMonthViewAtTravelStartUseCase renderCalendarMonthViewAtTravelStart;
  private final RenderCalendarMonthViewUseCase renderCalendarMonthView;
  private final RenderCalendarWeekViewUseCase renderCalendarWeekView;
  private final UpdateActivityTimeUseCase updateActivityTimeUseCase;
  private final LikeActivityUseCase likeActivityUseCase;
  private final DislikeActivityUseCase dislikeActivityUseCase;
  private final RemoveReactionActivityUseCase removeReactionActivityUseCase;
  private final DeleteActivityUseCase deleteActivityUseCase;
  private final AddActivityCostUseCase addActivityCostUseCase;
  private final UpdateActivityCostUseCase updateActivityCostUseCase;
  private final DeleteActivityCostUseCase deleteActivityCostUseCase;
  private final FindActivityCostsUseCase findActivityCostsUseCase;
  private final MoveActivityUseCase moveActivityUseCase;
  private final UpdateActivityUseCase updateActivityUseCase;
  private final FindActivityDetailsUseCase findActivityDetailsUseCase ;
  private final FindActivityReactionsUseCase findActivityReactionsUseCase;
  

  @ApiOperation("Allow to render the calendar month view")
  @GetMapping(value = "calendar/month/{year}/{month}", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public CalendarMonthView getMonthView(@PathVariable("travelId") final long tripId,
                                        @PathVariable("year") final int year,
                                        @PathVariable("month") final int month) {
	  return renderCalendarMonthView.getCalenderMonthView(CurrentUser.getCurrentUser(),tripId, month, year);
  }

  @ApiOperation("Allow to render the calendar month view positioned at the start of the travel")
  @GetMapping(value = "calendar/month", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public CalendarMonthView getMonthViewAtTripStart(@PathVariable("travelId") final long tripId
                                                   ) {
    return renderCalendarMonthViewAtTravelStart.getCalenderMonthView(CurrentUser.getCurrentUser(),tripId);
  }

  @ApiOperation("Allow to render the calendar week view based on parameters")
  @GetMapping(value = "calendar/month/{year}/{month}/{week}", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public CalendarWeekView getWeekView(
      @PathVariable("travelId") final long tripId,
      @PathVariable("year") final int year,
      @PathVariable("month") final int month,
      @PathVariable("week") final int week) {
	  return renderCalendarWeekView.getCalenderMonthView(CurrentUser.getCurrentUser(),tripId, month, year, week);
  }

  @ApiOperation("Update the activity day")
  @PatchMapping(value = "activity/{activityId}/day", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void moveActivity(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId,
      @RequestBody final MoveActivityRequest moveActivityRequest
  ) {
	  //moveActivityUseCase.moveActivity(tripId, activityId, moveActivityRequest);
  }

  @ApiOperation("Update the activity start and end time")
  @PatchMapping(value = "activity/{activityId}/time", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void updateActivityTime(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId,
      @RequestBody final PatchActivityTimeRequest moveActivityRequest
  ) {
	  updateActivityTimeUseCase.updateActivityTime(CurrentUser.getCurrentUser(),tripId, activityId, moveActivityRequest);
  }

  @ApiOperation("Likes an activity. If the activity has already a reaction from this user, the previous reaction is removed before adding the like")
  @PostMapping(value = "activity/{activityId}/reaction/thumb-up", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'GUEST')")
  public  ResourceCreationResponse  likeActivity(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId
  ) {
	  return new  ResourceCreationResponse(likeActivityUseCase.likeActivity(CurrentUser.getCurrentUser(),activityId));
  }

  @ApiOperation("Dislikes an activity. If the activity has already a reaction from this user, the previous reaction is removed before adding the dislike")
  @PostMapping(value = "activity/{activityId}/reaction/thumb-down", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'GUEST')")
  public  ResourceCreationResponse dislikeActivity(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId
  ) {
	  return new  ResourceCreationResponse(dislikeActivityUseCase.dislikeActivity(CurrentUser.getCurrentUser(),activityId));
  }

  @ApiOperation("Find activity details")
  @GetMapping(value = "activity/{activityId}", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'GUEST')")
  public ActivityDetails findActivityDetails(
      @PathVariable("travelId") final long travelId,
      @PathVariable("activityId") final long activityId) { 
	  
	  return findActivityDetailsUseCase.findActivityDetails(CurrentUser.getCurrentUser(),travelId, activityId);
  }

  @ApiOperation("Find activity reactions")
  @GetMapping(value = "activity/{activityId}/reactions", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'GUEST')")
  public ActivityReaction findActivityReactions (
      @PathVariable("travelId") final long travelId,
      @PathVariable("activityId") final long activityId) {
	  
	  return findActivityReactionsUseCase.findActivityReactions(activityId);
  }

  @ApiOperation("Remove reaction from activity")
  @DeleteMapping(value = "activity/{activityId}/reaction", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'GUEST')")
  public void removeReactionActivity(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId
  ) {

	  removeReactionActivityUseCase.removeReaction(CurrentUser.getCurrentUser(),activityId);
  }

  @ApiOperation("Remove activity from the plan")
  @DeleteMapping(value = "activity/{activityId}", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void removeActivity(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId
  ) {

	  deleteActivityUseCase.deleteActivity(tripId, activityId);
  }

  @ApiOperation("Update activity details")
  @PatchMapping(value = "activity/{activityId}", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  @ResponseBody
  public void updateActivity(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId,
      @RequestBody CreateActivityForm patchData
  ) {

	  updateActivityUseCase.updateActivity(CurrentUser.getCurrentUser(), tripId, activityId, patchData);
  }


  @ApiOperation("Add cost to a default activity")
  @PostMapping(value = "activity/{activityId}/costs", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public  ResourceCreationResponse addCost(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId,
      @Valid @RequestBody final AddCostRequest addCostRequest
  ) {
	  return new ResourceCreationResponse(addActivityCostUseCase.addCost(tripId, activityId, addCostRequest));
  }

  @ApiOperation("Updates cost to a default activity")
  @PatchMapping(value = "activity/{activityId}/costs/{id}", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void patchCost(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId,
      @PathVariable("id") final long id,
      @RequestBody AddCostRequest addCostRequest
  ) {
	  updateActivityCostUseCase.patchCost(CurrentUser.getCurrentUser(),id, addCostRequest);
  }


  @ApiOperation("Remove cost from a default activity")
  @DeleteMapping(value = "activity/{activityId}/costs/{id}", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void removeCost(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId,
      @PathVariable("id") final long costId
  ) {
	  deleteActivityCostUseCase.deleteCost(activityId,costId);
  }

  @ApiOperation("Get all costs linked to a specific activity")
  @GetMapping(value = "activity/{activityId}/costs", produces = "application/json")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public PageResponse<ActivityCost> getAllCosts(
      @PathVariable("travelId") final long tripId,
      @PathVariable("activityId") final long activityId,
      @RequestParam @Positive final int page,
      @RequestParam @Positive final int limit
  ) {
	  return new PageResponse<>(findActivityCostsUseCase.findCosts(tripId, activityId, page, limit));
  }

}
