package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreatePostRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class AddReplyToPostUseCase {

	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	public long addreplyforpost(final String username, final long travelId, final long Id,
			final CreatePostRequest createPostRequest) {

		Post p = postRepository.findById(Id).orElseThrow(EntityNotFoundException::new);

		final var body = createPostRequest;

		Reply r = new Reply();
		r.setMessage(body.getMessage());

		r.setCreatedBy(username);
		r.setPost(p);
		replyRepository.save(r);

		return r.getId();

	}

}
