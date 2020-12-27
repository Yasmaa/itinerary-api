package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.AccommodationDetails;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GetAccommodationUseCase {
	private final AccommodationRepository accommodationRepository;

	@Autowired
	private ModelMapper modelMapper;

	public AccommodationDetails readAccommodations(final long travelId, final long id) {

		return convertToAccommodationDetails(
				accommodationRepository.findById(id).orElseThrow(EntityNotFoundException::new));

	}

	private AccommodationDetails convertToAccommodationDetails(
			com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails ac) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		AccommodationDetails accommodation = modelMapper.map(ac, AccommodationDetails.class);
		return accommodation;

	}

}
