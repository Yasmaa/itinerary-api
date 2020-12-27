package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class DeleteActivityCostUseCase {
	private final CostRepository costRepository;
	private final ActivityRepository activityRepository;

	public void deleteCost(final long activityId, final long id) {

		activityRepository.deleteReferenceToCost(id);

		costRepository.deleteById(id);

	}

}
