package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.accounts.Participant;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.ParticipantRole;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.AccountRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.ParticipantRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchRoleRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class UpdateParticipantUseCase {
	private final AccountRepository accountRepository;
	private final ParticipantRepository participantRepository;
	private final TravelRepository travelRepository;

	@Autowired
	private ModelMapper modelMapper;

	public void updateParticipant(final String username, final long travelId, final long id,
			PatchRoleRequest patchRoleRequest) {

		final var body = patchRoleRequest;

		Participant p = participantRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Participant currentUser = participantRepository.findParticipant(username, travelId);
		if (body.getRole() != null) {
			if (body.getRole() == ParticipantRole.OWNER && currentUser.getRole() == ParticipantRole.OWNER) {
				p.setRole(body.getRole());
				currentUser.setRole(ParticipantRole.ADMIN);
				participantRepository.save(currentUser);
			} else if (body.getRole() == ParticipantRole.OWNER && currentUser.getRole() != ParticipantRole.OWNER) {

				throw new IllegalArgumentException("Action not allowed !");
			} else {

				p.setRole(body.getRole());
			}
		}
		p.setLastModifiedBy(username);
		p.setLastModifiedDate(LocalDateTime.now());
		participantRepository.save(p);

	}

}
