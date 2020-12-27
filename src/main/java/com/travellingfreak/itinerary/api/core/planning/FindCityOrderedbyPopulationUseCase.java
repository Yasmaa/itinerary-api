package com.travellingfreak.itinerary.api.core.planning;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.core.model.geo.City;
import com.travellingfreak.itinerary.api.dataproviders.model.geo.Toponym;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.ToponymRepository;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class FindCityOrderedbyPopulationUseCase {
	private final ToponymRepository toponymRepository;

	public Page<City> findCitiesOrderesbyPopulation(final long travelId, String searchText, final int page,
			final int limit) {

		Page<Toponym> place = toponymRepository.findCitiesOrderesbyPopulation(travelId, searchText,
				PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "population")));
		int totalElements = (int) place.getTotalElements();

		return new PageImpl<City>(place.stream().map(this::convertToCity).collect(Collectors.toList()),
				PageRequest.of(page, limit), totalElements);

	}

	private City convertToCity(Toponym ac) {
		City c = new City();
		c.setId(ac.getId());
		c.setName(ac.getName());
		return c;

	}

}
