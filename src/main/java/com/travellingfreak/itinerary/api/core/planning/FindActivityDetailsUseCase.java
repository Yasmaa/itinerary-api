package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.PassRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.LocationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.LikeRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.ActivityDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.Collapsedpost;
import com.travellingfreak.itinerary.api.entrypoints.responses.ReplyDetails;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class FindActivityDetailsUseCase {

	private final ActivityRepository activityRepository;
	private final LocationRepository locationRepository;
	private final LikeRepository likeRepository;
	private final ReplyRepository replyRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ActivityDetails findActivityDetails(final String username, final long travelId, final long activityId) {

		ActivityDetails ad = new ActivityDetails();
		Activity a = activityRepository.findById(activityId).orElseThrow(EntityNotFoundException::new);

		ad.setId(a.getId());
		ad.setCategory(a.getCategory());
		ad.setSubCategory(a.getSubCategory());
		ad.setTitle(a.getTitle());

		if (a.getPost() != null) {

			Collapsedpost cp = new Collapsedpost();
			List<Reply> replies = replyRepository.replies(a.getPost().getId());
			Integer count = replies.size();
			cp.setPost(a.getPost());
			if (!replies.isEmpty()) {

				modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
				ReplyDetails rp = modelMapper.map(replies.get(count - 1), ReplyDetails.class);
				cp.setLastreply(rp);
			}
			cp.setCount(count);
			ad.setDescription(cp);
		}

		ad.setStartTime(a.getStartTime());
		ad.setEndTime(a.getEndTime());
		if (a.getStartLocation() != null) {
			ad.setStartLocation(a.getStartLocation().getId());
		}
		if (a.getEndLocation() != null) {
			ad.setEndLocation(a.getEndLocation().getId());
		}
		ad.setDistanceCovered(a.getDistanceCovered());
		ad.setFlightNumber(a.getFlightNumber());
		ad.setGate(a.getGate());
		if (a.getPrice() != null) {
			ad.setPrice(a.getPrice().getAmount());
		}
		ad.setDislikeCount(likeRepository.findCountDislikeReactions(activityId));
		ad.setLikeCount(likeRepository.findCountLikeReactions(activityId));

		ad.setCreatedBy(new User(a.getCreatedBy(), null, null));

		ad.setCreatedOn(a.getCreatedDate());

		if (a.getLastModifiedBy() != null) {
			ad.setLastModifiedBy(new User(a.getLastModifiedBy(), null, null));
			ad.setLastModifiedOn(a.getLastModifiedDate());
		} else {
			ad.setLastModifiedBy(null);
		}

		Like like = likeRepository.findReactionForUser(username, activityId);
		if (like != null) {

			if (like.isThumbsUp()) {
				ad.setCurrentUserLikes(true);
			} else {
				ad.setCurrentUserDislikes(true);
			}

		}

		return ad;

	}

}
