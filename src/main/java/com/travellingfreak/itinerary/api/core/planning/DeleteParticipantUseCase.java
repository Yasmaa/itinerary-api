package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.accounts.Participant;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.ParticipantRole;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.AccountRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.ParticipantRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class DeleteParticipantUseCase {
	private final ParticipantRepository participantRepository;

	public void deleteParticipant(final long travelId, final long id) {

		Participant p = participantRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		if (p.getRole() == ParticipantRole.OWNER) {

			throw new IllegalArgumentException("Action not allowed !");

		} else {

			participantRepository.deleteById(id);

		}
	}

}
