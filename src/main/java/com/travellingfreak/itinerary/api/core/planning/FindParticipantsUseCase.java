package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.AccountRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.ParticipantRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.ParticipantDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class FindParticipantsUseCase {

	private final AccountRepository accountRepository;
	private final ParticipantRepository participantRepository;
	private final TravelRepository travelRepository;

	public Page<ParticipantDetails> listAccounts(final long travelId, final int page, final int limit) {
		final var id = travelRepository.findById(travelId).orElseThrow();
		return participantRepository.findByTravel(id, PageRequest.of(page, limit))
				.map(it -> new ParticipantDetails(it.getId(), new User(it.getAccountId(), null, null), it.getRole()));
	}

}
