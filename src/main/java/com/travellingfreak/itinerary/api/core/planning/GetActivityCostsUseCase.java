package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.ActivityCost;
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
public class GetActivityCostsUseCase {
	private final CostRepository costRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<ActivityCost> findCosts(final long tripId, final long activityId, final int page, final int limit) {

		Page<Cost> costs = costRepository.getAllcostsforActivity(tripId, activityId, PageRequest.of(page, limit));
		int totalElements = (int) costs.getTotalElements();

		return new PageImpl<ActivityCost>(costs.stream()

				.map(this::convertToCostDetails).collect(Collectors.toList()), PageRequest.of(page, limit),
				totalElements);
	}

	private ActivityCost convertToCostDetails(Cost c) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		ActivityCost cost = modelMapper.map(c, ActivityCost.class);
		return cost;

	}

}
