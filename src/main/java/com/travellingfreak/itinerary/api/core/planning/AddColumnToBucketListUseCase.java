package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Bucket;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.PassRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.LocationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.AddColumnRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class AddColumnToBucketListUseCase {
	private final BucketsRepository bucketsRepository;
	private final ActivityRepository activityRepository;
	private final TravelRepository travelRepository;
	private final PostRepository postRepository;
	private final PassRepository passrepository;
	private final CostRepository costrepository;
	private final DayRepository dayrepository;
	private final LocationRepository locationRepository;

	public long addbucket(final String username, final long travelId, final AddColumnRequest addColumnRequest) {

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		Bucket b = new Bucket();
		final var body = addColumnRequest;
		b.setName(body.getTitle());
		b.setTravel(t);
		b.setCreatedBy(username);
		bucketsRepository.save(b);
		return b.getId();

	}

}
