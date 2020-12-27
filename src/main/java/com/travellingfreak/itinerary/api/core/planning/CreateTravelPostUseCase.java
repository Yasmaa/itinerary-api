package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
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
public class CreateTravelPostUseCase {

	private final PostRepository postRepository;
	private final TravelRepository travelRepository;

	public long createTravelPost(final String username, final long travelId, final CreatePostRequest request) {

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		Post p = new Post();
		p.setMessage(request.getMessage());
		p.setCreatedBy(username);
		postRepository.save(p);
		t.setDescription(p);
		travelRepository.save(t);

		return p.getId();
	}

}
