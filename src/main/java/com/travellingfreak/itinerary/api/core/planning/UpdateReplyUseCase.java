package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreatePostRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class UpdateReplyUseCase {

	@Autowired
	private ModelMapper modelMapper;

	private final ReplyRepository replyRepository;

	public void updatereplyforpost(final String username, final long travelId, final long Id,
			final CreatePostRequest createPostRequest) {

		final var body = createPostRequest;

		Reply r = replyRepository.findById(Id).get();
		r.setMessage(body.getMessage());
		r.setLastModifiedBy(username);
		r.setLastModifiedDate(LocalDateTime.now());

		replyRepository.save(r);

	}

}
