package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchAccommodationRequest;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class UpdateAccommodationUseCase {
	private final AccommodationRepository accommodationRepository;
	private final TravelRepository travelRepository;
	private final BookingReferenceRepository bookingRepository;

	public void updateAccommodation(final String username, final long travelId, final long id,
			final PatchAccommodationRequest patchAccommodationRequest) {

		AccommodationDetails accommodation = accommodationRepository.CustomAccommoodation(travelId, id);

		if (accommodation != null) {

			final var body = patchAccommodationRequest;
			if (body.getName() != null) {

				accommodation.setName(body.getName());
			}
			if (body.getCountry() != null) {
				accommodation.setCountry(body.getCountry());
			}
			if (body.getCity() != null) {
				accommodation.setCity(body.getCity());
			}
			if (body.getAddress() != null) {
				accommodation.setAddress(body.getAddress());
			}
			if (body.getStreetAddress() != null) {
				accommodation.setStreetAddress(body.getStreetAddress());
			}
			if (body.getPostCode() != null) {
				accommodation.setPostCode(body.getPostCode());
			}
			if (body.getMinCheckIn() != null) {
				accommodation.setMinCheckIn(body.getMinCheckIn());
			}
			if (body.getMaxCheckOut() != null) {
				accommodation.setMaxCheckOut(body.getMaxCheckOut());
			}
			if (body.getNumberOfStars() != 0) {
				accommodation.setNumberOfStars(body.getNumberOfStars());
			}

			accommodation.setLastModifiedBy(username);
			accommodation.setLastModifiedDate(LocalDateTime.now());
			accommodationRepository.save(accommodation);

		}

	}

}
