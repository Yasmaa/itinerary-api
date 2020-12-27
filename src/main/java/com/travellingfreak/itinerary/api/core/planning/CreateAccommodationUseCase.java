package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateAccommodationRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class CreateAccommodationUseCase {
	private final AccommodationRepository accommodationRepository;
	private final TravelRepository travelRepository;

	public long createAccommodation(final String username, final long travelId,
			final CreateAccommodationRequest createAccommodationRequest) {

		AccommodationDetails accommodation = new AccommodationDetails();
		final var body = createAccommodationRequest;
		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		accommodation.setName(body.getName());
		accommodation.setCountry(body.getCountry());
		accommodation.setCity(body.getCity());
		accommodation.setAddress(body.getAddress());
		accommodation.setStreetAddress(body.getStreetAddress());
		accommodation.setPostCode(body.getPostCode());
		accommodation.setMinCheckIn(body.getMinCheckIn());
		accommodation.setMaxCheckOut(body.getMaxCheckOut());
		accommodation.setNumberOfStars(body.getNumberOfStars());
		t.setId(travelId);
		accommodation.setTravel(t);

		accommodation.setCustom(true);
		accommodation.setCreatedBy(username);
		accommodationRepository.save(accommodation);

		return accommodation.getId();
	}

}
