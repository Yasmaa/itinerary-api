package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.LikeRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.ActivityReaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class FindActivityReactionsUseCase {

	private final ActivityRepository activityRepository;
	private final LikeRepository likeRepository;

	public ActivityReaction findActivityReactions(final long activityId) {

		Activity t = activityRepository.findById(activityId).orElseThrow(EntityNotFoundException::new);

		ActivityReaction ar = new ActivityReaction();

		List<User> lk = likeRepository.findActivityLikes(activityId).stream().map(this::convertToUser)
				.collect(Collectors.toList());

		List<User> dk = likeRepository.findActivityDislikes(activityId).stream().map(this::convertToUser)
				.collect(Collectors.toList());

		ar.setLikes(lk);
		ar.setDislikes(dk);
		return ar;

	}

	private User convertToUser(Like l) {
		User user = new User();
		user.setId(l.getCreatedBy());
		return user;

	}

}
