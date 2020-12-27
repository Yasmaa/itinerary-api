package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.budget.Pass;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Location;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.PassRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.LocationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateActivityForm;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class UpdateActivityUseCase {

	private final BucketsRepository bucketsRepository;
	private final ActivityRepository activityRepository;
	private final TravelRepository travelRepository;
	private final PostRepository postRepository;
	private final PassRepository passrepository;
	private final CostRepository costrepository;
	private final DayRepository dayrepository;
	private final LocationRepository locationRepository;

	public void updateActivity(final String username, final long travelId, final long activityId,
			final CreateActivityForm createActivityForm) {

		final var body = createActivityForm;
		Activity a = activityRepository.findActivity(activityId);
		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);

		if (body.getPass() != 0) {

			Pass p = passrepository.findById(body.getPass()).orElseThrow(EntityNotFoundException::new);
			a.setPass(p);
		}

		Post ps = new Post();

		if (body.getDescription() != null) {
			ps.setMessage(body.getDescription());
			postRepository.save(ps);
		}

		if (body.getPrice() != 0) {
			Cost c = new Cost();
			c.setAmount(body.getPrice());
			c.setTravel(t);
			costrepository.save(c);
			a.setPrice(c);
		}

		if (body.getCategory() != null) {
			a.setCategory(body.getCategory());
		}
		if (body.getSubCategory() != null) {
			a.setSubCategory(body.getSubCategory());
		}
		if (body.getTitle() != null) {
			a.setTitle(body.getTitle());
		}
		a.setTravel(t);

		if (body.getStartLocation() != 0) {
			Location l1 = locationRepository.findById(body.getStartLocation()).get();
			a.setStartLocation(l1);
		}
		if (body.getEndLocation() != 0) {
			Location l2 = locationRepository.findById(body.getEndLocation()).get();
			a.setEndLocation(l2);
		}

		a.setPost(ps);

		if (body.getStartTime() != null) {
			a.setStartTime(body.getStartTime());
		}
		if (body.getEndTime() != null) {
			a.setEndTime(body.getEndTime());
		}

		if (body.getFlightNumber() != null) {
			a.setFlightNumber(body.getFlightNumber());
		}
		if (body.getGate() != null) {
			a.setGate(body.getGate());
		}
		if (body.getDistanceCovered() != null) {
			a.setDistanceCovered(body.getDistanceCovered());
		}

		a.setTravel(t);

		a.setLastModifiedBy(username);
		a.setLastModifiedDate(LocalDateTime.now());
		activityRepository.save(a);

	}

}
