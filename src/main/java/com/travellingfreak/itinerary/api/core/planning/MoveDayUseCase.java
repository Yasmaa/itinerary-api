package com.travellingfreak.itinerary.api.core.planning;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelSettingRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.MoveDayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class MoveDayUseCase {
	private final TravelRepository travelRepository;
	private final DayRepository dayRepository;
	private final BucketsRepository bucketsRepository;
	private final TravelSettingRepository travelSettingRepository;
	private final PostRepository postRepository;

	@Transactional
	public void moveDay(final long travelId, final long dayId, final MoveDayRequest moveDayRequest) {
		long newDayId;
		final var body = moveDayRequest;
		if (body.getAfterDayId() != 0) {
			newDayId = body.getAfterDayId();

		} else {
			newDayId = body.getBeforeDayId();
		}

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		Day d = dayRepository.findById(dayId).orElseThrow(EntityNotFoundException::new);
		Day dt = dayRepository.findById(newDayId).orElseThrow(EntityNotFoundException::new);

		if (dt.getDate() != null) {

			if (dt.getDate().isAfter(t.getStartDate()) && dt.getDate().isBefore(t.getEndDate())) {

				dt.setTravel(t);
				t.removeDay(d);
				t.addDay(dt);
				travelRepository.save(t);
				dayRepository.save(d);
				dayRepository.removeAssociation(dayId);

				dayRepository.updateActivityDay(dayId, newDayId);
				dayRepository.moveposts(dayId, newDayId);
			}

		}

	}

}
