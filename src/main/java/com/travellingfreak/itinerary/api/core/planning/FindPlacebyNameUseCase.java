package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.geo.Place;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Location;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class FindPlacebyNameUseCase {
	private final LocationRepository locationRepository;

	private String tag;

	public Page<Place> findPlacebyName(final long travelId, final String searchText, String t, final int page,
			final int limit) {

		tag = t;
		Page<Location> place = locationRepository.findplacebyname(searchText, PageRequest.of(page, limit));
		int totalElements = (int) place.getTotalElements();

		return new PageImpl<Place>(place.stream().map(this::convertToPlace).collect(Collectors.toList()),
				PageRequest.of(page, limit), totalElements);

	}

	private Place convertToPlace(Location ac) {
		Place p = new Place();
		p.setLocation(ac);
		p.setTag(tag);

		return p;

	}

}
