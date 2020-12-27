package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.AddCostRequest;
import org.springframework.stereotype.Component;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class AddActivityCostUseCase {
	private final CostRepository costRepository;
	private final TravelRepository travelRepository;
	private final ActivityRepository activityRepository;

	@Transactional
	public long addCost(final long tripId, final long activityId, final AddCostRequest addCostRequest) {

		Cost cost = new Cost();
		Travel t = travelRepository.findById(tripId).orElseThrow(EntityNotFoundException::new);
		Activity a = activityRepository.findById(activityId).orElseThrow(EntityNotFoundException::new);
		final var body = addCostRequest;
		cost.setNote(body.getNote());
		cost.setCurrency(body.getCurrencyCode());
		cost.setAmount(body.getPrice());
		cost.setTravel(t);
		cost.setParentActivity(a);

		// a.addFee(cost);
		costRepository.save(cost);
		activityRepository.save(a);
		return cost.getId();
	}

}
