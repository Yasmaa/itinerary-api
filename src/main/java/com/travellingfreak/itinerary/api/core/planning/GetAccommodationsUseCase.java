package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.AccommodationDetails;
import org.springframework.data.domain.PageImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GetAccommodationsUseCase {
	private final AccommodationRepository accommodationRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<AccommodationDetails> listAccommodations(final long travelId, final int page, final int limit) {

		Page<com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails> accomodationPage = accommodationRepository
				.findAccommoodations(travelId, PageRequest.of(page, limit));
		int totalElements = (int) accomodationPage.getTotalElements();

		return new PageImpl<AccommodationDetails>(
				accommodationRepository.findAccommoodations(travelId, PageRequest.of(page, limit)).stream()

						.map(this::convertToAccommodationDetails).collect(Collectors.toList()),
				PageRequest.of(page, limit), totalElements);
	}

	public Page<AccommodationDetails> searchAccommodations(final long travelId, final String name, final int page,
			final int limit) {

		Page<com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails> accomodationPage = accommodationRepository
				.searchAccommoodations(travelId, name, PageRequest.of(page, limit));
		int totalElements = (int) accomodationPage.getTotalElements();

		return new PageImpl<AccommodationDetails>(
				accommodationRepository.searchAccommoodations(travelId, name, PageRequest.of(page, limit)).stream()

						.map(this::convertToAccommodationDetails).collect(Collectors.toList()),
				PageRequest.of(page, limit), totalElements);
	}

	public AccommodationDetails readAccommodations(final long travelId, final long id) {

		return convertToAccommodationDetails(accommodationRepository.findById(id).get());

	}

	private AccommodationDetails convertToAccommodationDetails(
			com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails ac) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		AccommodationDetails userLocationDTO = modelMapper.map(ac, AccommodationDetails.class);
		return userLocationDTO;

	}

}
