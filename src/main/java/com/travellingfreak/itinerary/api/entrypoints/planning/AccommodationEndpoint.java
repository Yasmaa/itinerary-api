package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.model.PageResponse;
import com.travellingfreak.itinerary.api.core.planning.CreateAccommodationUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteAccommodationUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindAccommodationUseCase;
import com.travellingfreak.itinerary.api.core.planning.GetAccommodationsUseCase;

import com.travellingfreak.itinerary.api.core.planning.GetAccommodationUseCase;
import com.travellingfreak.itinerary.api.core.planning.ListAccommodationsUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateAccommodationUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateAccommodationRequest;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchAccommodationRequest;
import com.travellingfreak.itinerary.api.entrypoints.responses.AccommodationDetails;
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

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accommodations/{travelId}")
@Api(tags = "Accommodation API")
@RequiredArgsConstructor
public class AccommodationEndpoint {
	private final GetAccommodationsUseCase listaccommodations;
	private final GetAccommodationUseCase getAccommodationUseCase;
	private final DeleteAccommodationUseCase deleteAccommodationUseCase;
	private final CreateAccommodationUseCase createAccommodationUSeCase;
	private final UpdateAccommodationUseCase updateAccommodationUseCase;
	private final FindAccommodationUseCase findAccommodationUseCase;
	private final ListAccommodationsUseCase listAccommodationsUSeCase;
	
  @ApiOperation("Read accommodation")
  @GetMapping(value = "{id}", produces = "application/json")
  @Timed("api.accommodations.get")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public AccommodationDetails get(
      @PathVariable("travelId") final long travelId,
      @PathVariable("id") final long id) {
	  return getAccommodationUseCase.readAccommodations(travelId,id);
  }

  @ApiOperation("Delete accommodation. Only custom accommodation can be deleted")
  @DeleteMapping(value = "{id}", produces = "application/json")
  @Timed("api.accommodations.delete")
  @PreAuthorize("hasPermission(#travelId, '', 'ADMIN')")
  public void deleteAccommodation (
      @PathVariable("travelId") final long travelId,
      @PathVariable("id") final long id) {
	  deleteAccommodationUseCase.deleteAccommodation(travelId, id);
  }

  @ApiOperation("Add a custom accommodation linked to this travel")
  @PostMapping(value = "create", produces = "application/json")
  @Timed("api.accommodations.create")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public ResourceCreationResponse addAccommodation(
      @PathVariable("travelId") final long travelId,
      @RequestBody final @Valid CreateAccommodationRequest createAccommodationRequest) {
	  
	  
	  return new ResourceCreationResponse(createAccommodationUSeCase.createAccommodation(CurrentUser.getCurrentUser(),travelId, createAccommodationRequest));
  
  }



@ApiOperation("Patch a custom accommodation linked to this travel")
  @PatchMapping(value = "{id}", produces = "application/json")
  @Timed("api.accommodations.patch")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public void patchAccommodation(
      @PathVariable("travelId") final long travelId,
      @PathVariable("id") final long id,
      @RequestBody PatchAccommodationRequest patchAccommodationRequest) {
	  updateAccommodationUseCase.updateAccommodation(CurrentUser.getCurrentUser(),travelId, id, patchAccommodationRequest);
  }

  @ApiOperation("Search accommodation. Results should include either publicly available accommodations (custom_travel_id = null) or accommodations created "
                + "for this travel (custom_travel_id = travelId)")
  @GetMapping(value = "search", produces = "application/json")
  @Timed("api.accommodations.searchAccommodation")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public PageResponse<AccommodationDetails> searchAccommodation(
      @PathVariable("travelId") final long travelId,
      @RequestParam ("q") final String name,
      @RequestParam @Positive final int page,
      @RequestParam @Positive final int limit) {
	  return new PageResponse<>(findAccommodationUseCase.searchAccommodations(travelId,name,page,limit));
  }
  
   

  @ApiOperation("Returns the list of accommodations which have a reference with this travel, i.e. there is at least one booking reference linked to it")
  @GetMapping(value = "list", produces = "application/json")
  @Timed("api.accommodations.list")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public PageResponse<AccommodationDetails> listAccommodations(
      @PathVariable("travelId") final long travelId,
      @RequestParam @Positive final int page,
      @RequestParam @Positive final int limit) {
    
	  return new PageResponse<>(listAccommodationsUSeCase.listAccommodations(travelId,page,limit));

  }

}
