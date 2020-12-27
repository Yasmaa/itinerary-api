package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.accounts.Participant;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.ParticipantRole;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.AccountRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.ParticipantRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.ParticipantRequest;
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
public class AddParticipantUseCase {
	private final AccountRepository accountRepository;
	private final ParticipantRepository participantRepository;
	private final TravelRepository travelRepository;

	public long addParticipant(final String username, final long travelId,
			final ParticipantRequest participantRequest) {

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);

		final var body = participantRequest;
		if (participantRepository.findParticipant(body.getUserId(), travelId) == null) {
			Participant p = new Participant();

			p.setAccountId(body.getUserId());
			if (body.getRole() == ParticipantRole.OWNER) {
				throw new IllegalArgumentException("Role not allowed !");
			} else {
				p.setRole(body.getRole());
			}
			p.setTravel(t);
			p.setCreatedBy(username);
			participantRepository.save(p);
			return p.getId();
		} else {

			throw new IllegalArgumentException("Participant already exists !");
		}

	}

}
