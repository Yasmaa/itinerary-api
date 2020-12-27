package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.budget.Pass;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Location;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class AddNewActivityToADayUseCase {
	private final BucketsRepository bucketsRepository;
	private final ActivityRepository activityRepository;
	private final TravelRepository travelRepository;
	private final PostRepository postRepository;
	private final PassRepository passrepository;
	private final CostRepository costrepository;
	private final DayRepository dayrepository;
	private final LocationRepository locationRepository;

	@Transactional
	public long addActivitytoAday(final String username, final long travelId, final long dayId,
			final CreateActivityForm createActivityForm) {

		final var body = createActivityForm;
		Activity a = new Activity();
		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		Day d = dayrepository.findById(dayId).orElseThrow(EntityNotFoundException::new);

		if (body.getPass() != 0) {

			Pass p = passrepository.findById(body.getPass()).orElseThrow(EntityNotFoundException::new);
			a.setPass(p);
		}

		Post ps = new Post();
		Cost c = new Cost();

		ps.setMessage(body.getDescription());
		ps.setCreatedBy(username);
		postRepository.save(ps);

		c.setAmount(body.getPrice());
		c.setTravel(t);
		c.setCreatedBy(username);

		a.setCategory(body.getCategory());
		a.setSubCategory(body.getSubCategory());
		a.setTitle(body.getTitle());
		a.setTravel(t);

		if (body.getStartLocation() != 0) {
			Location l1 = locationRepository.findById(body.getStartLocation()).get();
			a.setStartLocation(l1);
		}
		if (body.getEndLocation() != 0) {
			Location l2 = locationRepository.findById(body.getEndLocation()).orElseThrow(EntityNotFoundException::new);
			a.setEndLocation(l2);
		}

		a.setPost(ps);
		a.setStartTime(body.getStartTime());
		a.setEndTime(body.getEndTime());

		a.setFlightNumber(body.getFlightNumber());
		a.setGate(body.getGate());
		a.setDistanceCovered(body.getDistanceCovered());

		a.setTravel(t);

		costrepository.save(c);

		a.setPrice(c);
		a.setDay(d);

		a.setCreatedBy(username);
		activityRepository.save(a);

		dayrepository.findById(dayId).orElseThrow(EntityNotFoundException::new).addActivity(a);
		return a.getId();

	}

}
