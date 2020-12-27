package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class DeleteActivityUseCase {

	private final ActivityRepository activityRepository;
	private final DayRepository dayRepository;

	public void deleteActivity(final long tripId, final long activityId) {

		activityRepository.deleteById(activityId);

	}

}
