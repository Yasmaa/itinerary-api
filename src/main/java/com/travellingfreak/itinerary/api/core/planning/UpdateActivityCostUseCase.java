package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.ActivityCost;
import com.travellingfreak.itinerary.api.entrypoints.requests.AddCostRequest;
import org.springframework.data.domain.PageImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class UpdateActivityCostUseCase {
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
		ActivityCost userLocationDTO = modelMapper.map(c, ActivityCost.class);
		return userLocationDTO;

	}

	public void addCost(final long tripId, final long activityId, final AddCostRequest addCostRequest) {

		Cost cost = new Cost();
		Travel t = new Travel();
		t.setId(tripId);
		Activity a = new Activity();
		a.setId(activityId);
		final var body = addCostRequest;
		cost.setNote(body.getNote());
		cost.setCurrency(body.getCurrencyCode());
		cost.setAmount(body.getPrice());
		cost.setTravel(t);
		cost.setParentActivity(a);

		costRepository.save(cost);
	}

	public void deleteCost(final long id) {

		costRepository.deleteById(id);
	}

	public void patchCost(final String username, final long costId, final AddCostRequest addCostRequest) {

		Cost cost = costRepository.findById(costId).get();
		final var body = addCostRequest;
		cost.setNote(body.getNote());
		cost.setCurrency(body.getCurrencyCode());
		cost.setAmount(body.getPrice());
		cost.setLastModifiedBy(username);
		cost.setLastModifiedDate(LocalDateTime.now());
		costRepository.save(cost);
	}

}
