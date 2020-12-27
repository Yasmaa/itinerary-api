package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.planning.AddActivityToBucketListUseCase;
import com.travellingfreak.itinerary.api.core.planning.AddColumnToBucketListUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteActivityAndAssociationToBucketListUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteAllActivitiesAndAssociationToBucketListUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteColumnFromBucketListUseCase;
import com.travellingfreak.itinerary.api.core.planning.GetBucketListforTripUseCase;
import com.travellingfreak.itinerary.api.core.planning.MoveActivityWithinBucketListUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.entrypoints.requests.AddColumnRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateActivityForm;
import com.travellingfreak.itinerary.api.entrypoints.requests.MoveBucketActivityRequest;
import com.travellingfreak.itinerary.api.entrypoints.responses.BucketListDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.ResourceCreationResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/buckets/{travelId}")
@Api(tags = "Buckets API")
@RequiredArgsConstructor
public class BucketsEndpoint {
	
  private final GetBucketListforTripUseCase getBucketListforTrip;
  private final AddActivityToBucketListUseCase addActivityToBucketListUseCase;
  private final AddColumnToBucketListUseCase addColumnToBucketListUseCase;
  private final DeleteColumnFromBucketListUseCase deleteColumnFromBucketListUseCase;
  private final DeleteActivityAndAssociationToBucketListUseCase deleteActivityAndAssociationToBucketList;
  private final DeleteAllActivitiesAndAssociationToBucketListUseCase deleteAllActivitiesAndAssociationToBucketList;
  private final MoveActivityWithinBucketListUseCase moveActivityWithinBucketListUseCase;
  @ApiOperation("Get the current bucket list for a given trip")
  @GetMapping(value = "bucket-list", produces = "application/json")
  @Timed("api.bucket-list.get")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public BucketListDetails get(@PathVariable("travelId") final long travelId) {
	    
	  return getBucketListforTrip.listBuckets(travelId);
  }

  @ApiOperation("Creates an activity and add it to the bucket list")
  @PostMapping(value = "bucket-list/{columnId}", produces = "application/json")
  @Timed("api.bucket-list.add")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public  ResourceCreationResponse addItem(@PathVariable("travelId") final long travelId,
                                                @PathVariable("columnId") final long columnId,
                                                @RequestBody final CreateActivityForm createActivityForm) {
	  return new  ResourceCreationResponse(addActivityToBucketListUseCase.addActivitytoBucketList(CurrentUser.getCurrentUser(),travelId, columnId, createActivityForm));
  }

  @ApiOperation("Creates a new column")
  @PostMapping(value = "/bucket-list", produces = "application/json")
  @Timed("api.bucket-list.add-column")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public ResourceCreationResponse addColumn(@PathVariable("travelId") final long travelId,
                                                  @RequestBody final AddColumnRequest addColumnRequest) {
	  return new ResourceCreationResponse(addColumnToBucketListUseCase.addbucket(CurrentUser.getCurrentUser(),travelId, addColumnRequest));
  }

  @ApiOperation("Deletes the column and the association to the bucket list")
  @DeleteMapping(value = "bucket-list/{id}", produces = "application/json")
  @Timed("api.bucket-list.remove-column")
  @PreAuthorize("hasPermission(#travelId, '', 'DELETE')")
  public void deleteColumn(@PathVariable("travelId") final long travelId,
                           @PathVariable("id") final long columnId) {
	  deleteColumnFromBucketListUseCase.deleteColumn(columnId);
  }

  @ApiOperation("Deletes the activity and the association to the bucket list")
  @DeleteMapping(value = "bucket-list/{columnId}/{id}", produces = "application/json")
  @Timed("api.bucket-list.remove-item")
  @PreAuthorize("hasPermission(#travelId, '', 'DELETE')")
  public void removeItem(@PathVariable("travelId") final long travelId,
                         @PathVariable("columnId") final long columnId,
                         @PathVariable("id") final long activityId) {
	  deleteActivityAndAssociationToBucketList.deleteActivity(activityId);
  }

  @ApiOperation("Deletes all the activities and the association to the bucket list")
  @DeleteMapping(value = "bucket-list", produces = "application/json")
  @Timed("api.bucket-list.clearAll")
  @PreAuthorize("hasPermission(#travelId, '', 'DELETE')")
  public void clearAll(@PathVariable("travelId") final long travelId) {
	  deleteAllActivitiesAndAssociationToBucketList.deleteActivities(travelId);
  }

  @ApiOperation("Move activity within columns")
  @PatchMapping(value = "bucket-list/{activity}", produces = "application/json")
  @Timed("api.bucket-list.clearAll")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void move(@PathVariable("travelId") final long travelId,
                   @PathVariable("activity") final long activityId,
                   @RequestBody final MoveBucketActivityRequest request) {
	  moveActivityWithinBucketListUseCase.moveActivity(travelId, activityId, request);
  }

}
