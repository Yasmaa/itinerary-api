package com.travellingfreak.itinerary.api.core.planning;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelSettingRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchDayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class UpdatePlanningSlotUseCase {
	private final TravelRepository travelRepository;
	private final DayRepository dayRepository;
	private final BucketsRepository bucketsRepository;
	private final TravelSettingRepository travelSettingRepository;
	private final PostRepository postRepository;

	public void modifyPlanningSlot(final String username, final long travelId, final long dayId,
			final PatchDayRequest request) {

		final var body = request;
		Day d = dayRepository.findById(dayId).orElseThrow(EntityNotFoundException::new);
		if (body.getTitle() != null) {
			d.setTitle(body.getTitle());
		}
		dayRepository.save(d);
		Post p = new Post();
		if (body.getNotes() != null) {
			p.setMessage(body.getNotes());
			p.setCreatedBy(username);

			d.setLastModifiedBy(username);
			d.setLastModifiedDate(LocalDateTime.now());
			postRepository.save(p);
			dayRepository.save(d);
			dayRepository.removeAssociationToPosts(dayId);
			dayRepository.addpostday(d.getId(), p.getId());
		}
	}

}
