package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class DeleteAccommodationUseCase {
	private final AccommodationRepository accommodationRepository;
	private final TravelRepository travelRepository;
	private final BookingReferenceRepository bookingRepository;

	public void deleteAccommodation(final long travelId, final long id) {

		AccommodationDetails accommodation = accommodationRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);

		if (accommodation != null && accommodation.isCustom()) {
			bookingRepository.deleteBookings(id);
			accommodationRepository.delete(accommodation);

		} else {

			throw new IllegalArgumentException("Action not allowed !");
		}

	}

}
