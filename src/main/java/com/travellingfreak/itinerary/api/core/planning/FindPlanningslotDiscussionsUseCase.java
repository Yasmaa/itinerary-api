package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.Collapsedpost;
import com.travellingfreak.itinerary.api.entrypoints.responses.ReplyDetails;

import org.springframework.data.domain.PageImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class FindPlanningslotDiscussionsUseCase {
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	private final GetAccommodationsUseCase acc;

	@Autowired
	private ModelMapper modelMapper;

	public Page<Collapsedpost> planningDiscussions(final long travelId, final long dayId, final int page,
			final int limit) {

		

		Page<Post> posts = postRepository.findPostsforDay(dayId, PageRequest.of(page, limit));
		int totalElements = (int) posts.getTotalElements();

		return new PageImpl<Collapsedpost>(postRepository.findPostsforDay(dayId, PageRequest.of(page, limit)).stream()

				.map(this::convert).collect(Collectors.toList()), PageRequest.of(page, limit), totalElements);

	}

	private Collapsedpost convert(Post ac) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		Collapsedpost test = new Collapsedpost();

		List<Reply> replies = replyRepository.replies(ac.getId());
		Integer count = replies.size();
		test.setPost(ac);
		if (!replies.isEmpty()) {
			ReplyDetails rp = modelMapper.map(replies.get(count - 1), ReplyDetails.class);
			test.setLastreply(rp);
		}
		test.setCount(count);

		return test;

	}

}
