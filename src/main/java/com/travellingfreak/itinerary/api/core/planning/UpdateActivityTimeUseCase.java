package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchActivityTimeRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class UpdateActivityTimeUseCase {

	private final ActivityRepository activityRepository;
	private final DayRepository dayRepository;

	public void updateActivityTime(final String username, final long tripId, final long activityId,
			final PatchActivityTimeRequest moveActivityRequest) {

		Activity activity = activityRepository.findById(activityId).orElseThrow(EntityNotFoundException::new);
		final var body = moveActivityRequest;

		activity.setStartTime(body.getStart());
		activity.setEndTime(body.getEnd());
		activity.setLastModifiedBy(username);
		activity.setLastModifiedDate(LocalDateTime.now());
		activityRepository.save(activity);

	}

}
