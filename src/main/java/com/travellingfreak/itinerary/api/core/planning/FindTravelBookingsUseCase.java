package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.BookingReference;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.AccommodationDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.BookingDetails;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class FindTravelBookingsUseCase {
	private final BookingReferenceRepository bookingReferenceRepository;
	private final AccommodationRepository accommodationRepository;

	private final GetAccommodationsUseCase acc;

	@Autowired
	private ModelMapper modelMapper;

	public List<BookingDetails> listBookings(final long travelId) {

		

		return bookingReferenceRepository.findBookings(travelId).stream()

				.map(this::convertToBookingDetails).collect(Collectors.toList());

	}

	private BookingDetails convertToBookingDetails(BookingReference ac) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		AccommodationDetails acdetails = acc.readAccommodations(ac.getTravel().getId(), ac.getDetails().getId());

		BookingDetails b = new BookingDetails();
		b.setId(ac.getId());
		b.setAccommodationDetails(acdetails);
		b.setAmount(ac.getCost().getAmount());
		b.setBookedThrough(ac.getBookedThrough());
		b.setConfirmation(ac.getConfirmation());
		b.setStartDate(ac.getStartDate());
		b.setEndDate(ac.getEndDate());
		b.setCurrencyCode(ac.getCost().getCurrency());
		b.setMaxCheckOutTime(ac.getMaxCheckOutTime());
		b.setMinCheckInTime(ac.getMinCheckInTime());
		b.setCreatedBy(new User(ac.getCreatedBy(), null, null));
		b.setCreatedOn(ac.getCreatedDate());

		if (ac.getLastModifiedBy() != null) {
			b.setLastModifiedBy(new User(ac.getLastModifiedBy(), null, null));
			b.setLastModifiedOn(ac.getLastModifiedDate());

		} else {
			b.setLastModifiedBy(null);
			b.setLastModifiedOn(null);
		}

		return b;
	}

}
