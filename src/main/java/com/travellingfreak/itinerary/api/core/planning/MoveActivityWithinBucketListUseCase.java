package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.MoveBucketActivityRequest;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class MoveActivityWithinBucketListUseCase {

	private final BucketsRepository bucketsRepository;
	private final ActivityRepository activityRepository;

	@Transactional
	public void moveActivity(final long travelId, final long activityId, final MoveBucketActivityRequest body) {

		final var destinationPosition = body.getDestinationPosition();
		final var activity = activityRepository.findById(activityId).orElseThrow();

		final var sourceBucket = activity.getBucket();
		if (sourceBucket.getId() == body.getDestinationBucket()) {
			// move within same bucket.
			activityRepository.updatePositionsBy(-1, activity.getTravel(), sourceBucket, activity.getBucketPosition());
			activityRepository.updatePositionsBy(+1, activity.getTravel(), sourceBucket, destinationPosition);
		} else {
			// remove from the bucket and add to the other one.
			final var destinationBucket = bucketsRepository.findById(body.getDestinationBucket()).orElseThrow();
			activityRepository.updatePositionsBy(-1, activity.getTravel(), sourceBucket, activity.getBucketPosition());
			activityRepository.updatePositionsBy(1, activity.getTravel(), destinationBucket, destinationPosition);
			activity.setBucket(destinationBucket);
		}
		activity.setBucketPosition(destinationPosition);

		activityRepository.save(activity);

	}

}
