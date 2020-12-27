package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.BookingReference;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.BookAccommodationRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class AddBookingUseCase {
	private final BookingReferenceRepository bookingReferenceRepository;
	private final CostRepository costRepository;
	private final AccommodationRepository accommodationRepository;
	private final TravelRepository travelRepository;

	public long bookAccommodation(final String username, final long travelId,
			final BookAccommodationRequest bookAccommodationRequest) {

		Cost c = new Cost();
		BookingReference b = new BookingReference();
		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		final var body = bookAccommodationRequest;
		c.setAmount(body.getAmount());
		c.setCurrency(body.getCurrencyCode());
		c.setTravel(t);

		costRepository.save(c);

		com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails accommodation1 = accommodationRepository
				.findById(body.getAccommodationId()).orElseThrow(EntityNotFoundException::new);

		b.setBookedThrough(body.getBookedThrough());
		b.setConfirmation(body.getConfirmation());
		b.setCost(c);
		b.setDetails(accommodation1);
		b.setStartDate(body.getStartDate());
		b.setEndDate(body.getEndDate());
		b.setMaxCheckOutTime(body.getMaxCheckOutTime());
		b.setMinCheckInTime(body.getMinCheckInTime());
		b.setTravel(t);

		b.setCreatedBy(username);
		bookingReferenceRepository.save(b);

		return b.getId();

	}

}
