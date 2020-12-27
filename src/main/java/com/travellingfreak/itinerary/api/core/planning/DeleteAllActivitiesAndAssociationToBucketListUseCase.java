package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class DeleteAllActivitiesAndAssociationToBucketListUseCase {
	private final BucketsRepository bucketsRepository;
	private final ActivityRepository activityRepository;

	public void deleteActivities(final long travelId) {

		activityRepository.deleteActivitiescosts(travelId);
		activityRepository.deleteActivitieslikes(travelId);
		activityRepository.deleteActivities(travelId);
		bucketsRepository.deleteBucketList(travelId);
	}

}
