package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.Expandpost;
import com.travellingfreak.itinerary.api.entrypoints.responses.ReplyDetails;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class FindPostUseCase {

	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public Expandpost expandedPost(final long postId) {

		Post p = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
		List<Reply> replies = replyRepository.replies(postId);
		Integer count = replies.size();
		return convert(p, replies);

	}

	private Expandpost convert(Post ac, List<Reply> r) {

		List<ReplyDetails> replies = r.stream().map(this::convertToReply).collect(Collectors.toList());
		Expandpost test = new Expandpost();
		test.setPost(ac);
		test.setReplies(replies);

		return test;

	}

	private ReplyDetails convertToReply(Reply r) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		ReplyDetails rp = modelMapper.map(r, ReplyDetails.class);
		return rp;

	}

}
