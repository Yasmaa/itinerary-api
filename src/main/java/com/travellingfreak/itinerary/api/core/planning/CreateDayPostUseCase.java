package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreatePostRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class CreateDayPostUseCase {

	private final PostRepository postRepository;
	private final DayRepository dayRepository;

	public long addpostforaday(final String username, final long travelId, final long dayId,
			final CreatePostRequest createPostRequest) {

		Day day = dayRepository.findById(dayId).orElseThrow(EntityNotFoundException::new);
		if (day == null) {
			Travel t = new Travel();
			t.setId(travelId);
			day = new Day();
			day.setId(dayId);
			day.setCreatedBy(username);

		}

		final var body = createPostRequest;

		Post p = new Post();
		p.setMessage(body.getMessage());
		p.setCreatedBy(username);
		postRepository.save(p);

		postRepository.test(day.getId(), p.getId());
		dayRepository.save(day);

		return day.getId();

	}

}
