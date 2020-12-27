package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Date: 7/26/2020 Time: 8:27 PM
 */
@Component
@RequiredArgsConstructor
public class DeletePostUseCase {

	private final PostRepository postRepository;
	private final TravelRepository travelRepository;

	public void deletepost(final long travelId, final long Id) {

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		if (t.getDescription().getId() == Id) {
			t.setDescription(null);
			travelRepository.save(t);
		}
		postRepository.deletePostDayAssociation(Id);
		postRepository.deleteById(Id);

	}

}
