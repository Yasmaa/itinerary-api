package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.MoveActivityRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class MoveActivityUseCase {

	private final ActivityRepository activityRepository;
	private final DayRepository dayRepository;

	public void moveActivity(final String username, final long travelId, final long activityId,
			final MoveActivityRequest moveActivityRequest) {

		final var body = moveActivityRequest;

		Activity activity = activityRepository.findById(activityId).orElseThrow(EntityNotFoundException::new);
		Day d = dayRepository.findById(body.getDestinationDay()).orElseThrow(EntityNotFoundException::new);
		activity.setDay(d);
		activity.setLastModifiedBy(username);
		activity.setLastModifiedDate(LocalDateTime.now());
		activityRepository.save(activity);
		d.addActivity(activity);
		d.setLastModifiedBy(username);
		d.setLastModifiedDate(LocalDateTime.now());
		dayRepository.save(d);

	}

}
