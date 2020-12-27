package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.LikeRepository;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class RemoveReactionActivityUseCase {
	private final LikeRepository likeRepository;

	public void removeReaction(final String username, final long activityId) {

		Like like = likeRepository.findReactionForUser(username, activityId);


		if (like != null) {

			likeRepository.delete(like);

		}

	}

}
