package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreatePostRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class UpdatePostUseCase {

	@Autowired
	private ModelMapper modelMapper;

	private final PostRepository postRepository;
	private final DayRepository dayRepository;
	private final ReplyRepository replyRepository;
	private final TravelRepository travelRepository;

	public void editpost(final String username, final long travelId, final long Id,
			final CreatePostRequest createPostRequest) {

		Post p = postRepository.findById(Id).orElseThrow(EntityNotFoundException::new);

		final var body = createPostRequest;

		p.setMessage(body.getMessage());
		p.setLastModifiedBy(username);
		p.setLastModifiedDate(LocalDateTime.now());
		postRepository.save(p);

	}

}
