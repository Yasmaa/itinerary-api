package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.LikeRepository;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class DislikeActivityUseCase {
	private final LikeRepository likeRepository;
	private final ActivityRepository activityRepository;

	public long dislikeActivity(final String username, final long activityId) {

		Activity ac = activityRepository.findById(activityId).orElseThrow(EntityNotFoundException::new);

		Like like = likeRepository.findReactionForUser(username, activityId);
		if (like != null) {

			likeRepository.delete(like);

		}

		Like lk = new Like();
		lk.setThumbsUp(false);
		lk.setActivity(ac);
		lk.setCreatedBy(username);
		likeRepository.save(lk);

		return lk.getId();

	}

}
