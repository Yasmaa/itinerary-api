package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.BookingReference;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.BookAccommodationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class UpdateBookingUseCase {
	private final BookingReferenceRepository bookingReferenceRepository;
	private final CostRepository costRepository;
	private final AccommodationRepository accommodationRepository;
	private final TravelRepository travelRepository;

	private final GetAccommodationsUseCase acc;

	@Autowired
	private ModelMapper modelMapper;

	public void upateBooking(final String username, final long travelId, final long id,
			final BookAccommodationRequest bookAccommodationRequest) {

		BookingReference b = bookingReferenceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Cost c;

		if (b.getCost() != null) {
			c = costRepository.findById(b.getCost().getId()).get();
		} else {
			c = new Cost();
		}
		final var body = bookAccommodationRequest;

		if (body.getAmount() != 0) {
			c.setAmount(body.getAmount());
			if (body.getCurrencyCode() != null) {
				c.setCurrency(body.getCurrencyCode());
			}

			b.setCost(c);
		}

		// c.setTravel(t);

		if (body.getAccommodationId() != 0) {
			com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails accommodation = accommodationRepository
					.findById(body.getAccommodationId()).get();
			b.setDetails(accommodation);
		}

		if (body.getBookedThrough() != null) {
			b.setBookedThrough(body.getBookedThrough());
		}

		if (body.getConfirmation() != null) {
			b.setConfirmation(body.getConfirmation());
		}

		if (body.getStartDate() != null) {
			b.setStartDate(body.getStartDate());
		}
		if (body.getEndDate() != null) {
			b.setEndDate(body.getEndDate());
		}
		if (body.getMaxCheckOutTime() != null) {
			b.setMaxCheckOutTime(body.getMaxCheckOutTime());
		}
		if (body.getMinCheckInTime() != null) {
			b.setMinCheckInTime(body.getMinCheckInTime());
		}

		b.setLastModifiedBy(username);
		b.setLastModifiedDate(LocalDateTime.now());

		bookingReferenceRepository.save(b);

	}

}
