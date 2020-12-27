package com.travellingfreak.itinerary.api.core.planning;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.DaySettings;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelSettingRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreateDayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class CreatePlanningSlotUseCase {
	private final TravelRepository travelRepository;
	private final DayRepository dayRepository;
	private final BucketsRepository bucketsRepository;
	private final TravelSettingRepository travelSettingRepository;
	private final PostRepository postRepository;

	public long addPlanningSlot(final String username, final long travelId, final CreateDayRequest request) {

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		final var body = request;
		if (body.getBeforeDayId() != 0) {
			Day d = dayRepository.findById(body.getBeforeDayId()).orElseThrow(EntityNotFoundException::new);
			d.setLastModifiedBy(username);
			d.setLastModifiedDate(LocalDateTime.now());
			d.setTravel(t);
			d.setCreatedBy(username);
			t.addDay(d);
			Post p = new Post();
			if (body.getNotes() != null) {
				p.setMessage(body.getNotes());
				p.setCreatedBy(username);
				dayRepository.save(d);
				travelRepository.save(t);
				postRepository.save(p);
				dayRepository.addpostday(d.getId(), p.getId());
			} else {
				dayRepository.save(d);
				travelRepository.save(t);
			}

			return d.getId();
		} else {

			Day d;
			int count = (int) ChronoUnit.DAYS.between(t.getStartDate(), t.getEndDate());
			if (t.getStartDate() != null && t.getEndDate() != null) {
				t.setEndDate(t.getEndDate().plusDays(1));
				travelRepository.save(t);
				d = new Day(0L, t, (count + 1), t.getEndDate(), "Day " + (count + 1), new DaySettings(),
						new ArrayList<>());

			} else {
				d = new Day(0L, t, (count + 1), null, "Day " + (count + 1), new DaySettings(), new ArrayList<>());
			}

			if (body.getNotes() != null) {

				Post p = new Post();
				p.setMessage(body.getNotes());
				p.setCreatedBy(username);

				dayRepository.save(d);
				postRepository.save(p);
				dayRepository.addpostday(d.getId(), p.getId());
			} else {
				dayRepository.save(d);
			}

			return d.getId();
		}

	}

}
