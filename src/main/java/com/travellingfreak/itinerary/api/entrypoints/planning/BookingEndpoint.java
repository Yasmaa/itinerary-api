package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.planning.AddBookingUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteBookingUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindTravelBookingsUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateBookingUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.entrypoints.requests.BookAccommodationRequest;
import com.travellingfreak.itinerary.api.entrypoints.responses.BookingDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.ResourceCreationResponse;

import java.util.List;

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
@RequestMapping("/api/booking/{travelId}")
@Api(tags = "Booking API")
@RequiredArgsConstructor
public class BookingEndpoint {
	
  private final AddBookingUseCase addBookingUseCase;
  private final UpdateBookingUseCase updateBookingUseCase;
  private final DeleteBookingUseCase deleteBookingUseCase;
  private final FindTravelBookingsUseCase findTravelBookingsUseCase;

  @ApiOperation("Link an accommodation to a travel for the specified duration and linked cost")
  @PostMapping(value = "", produces = "application/json")
  @Timed("api.planning.view.planning.overview")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public  ResourceCreationResponse bookAccommodation(@PathVariable("travelId") final long travelId,
                                @RequestBody BookAccommodationRequest bookAccommodationRequest) {
    
	 return new ResourceCreationResponse(addBookingUseCase.bookAccommodation(CurrentUser.getCurrentUser(),travelId, bookAccommodationRequest));
  }

  @ApiOperation("Updates a booking")
  @PatchMapping(value = "{id}", produces = "application/json")
  @Timed("api.planning.view.planning.overview")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void updateAccommodation(@PathVariable("travelId") final long travelId,
                                  @PathVariable("id") final long bookingId,
                                  @RequestBody BookAccommodationRequest bookAccommodationRequest) {
	  updateBookingUseCase.upateBooking(CurrentUser.getCurrentUser(),travelId, bookingId, bookAccommodationRequest);
  }

  @ApiOperation("Deletes a booking")
  @DeleteMapping(value = "{id}", produces = "application/json")
  @Timed("api.planning.view.planning.overview")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void deleteAccommodation(@PathVariable("travelId") final long travelId,
                                  @PathVariable("id") final long bookingId) {
	  deleteBookingUseCase.deleteBooking(bookingId);
  }

  @ApiOperation("List all bookings for a given travel")
  @GetMapping(value = "", produces = "application/json")
  @Timed("api.planning.view.planning.overview")
  @PreAuthorize("hasPermission(#travelId, '', 'READ')")
  public List<BookingDetails> listAccommodations(
		  @PathVariable("travelId") final long travelId
		 ){
    
	  return findTravelBookingsUseCase.listBookings(travelId);
  }

}
