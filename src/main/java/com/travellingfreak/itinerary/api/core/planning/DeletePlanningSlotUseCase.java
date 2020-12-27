package com.travellingfreak.itinerary.api.core.planning;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Bucket;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelSettingRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class DeletePlanningSlotUseCase {
	private final DayRepository dayRepository;
	private final BucketsRepository bucketsRepository;

	public void deleteDay(final long travelId, final long dayId, final boolean bl) {

		Day d = dayRepository.findById(dayId).orElseThrow(EntityNotFoundException::new);
		d.setTravel(null);
		dayRepository.save(d);
		dayRepository.removeAssociation(dayId);
		dayRepository.removeAssociationToPosts(dayId);
		dayRepository.removeIdfromActivities(dayId);
		dayRepository.delete(d);
		if (bl == true) {

			dayRepository.deleteAllActivities(dayId);

		} else {
			Bucket b = new Bucket();
			bucketsRepository.save(b);
			dayRepository.addActivitiestoBucketList(dayId, b.getId());
		}

	}

}
