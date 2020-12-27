package com.travellingfreak.itinerary.api.core.planning;

import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class MarkTravelArchivedUseCase {
	private final TravelRepository travelRepository;

	public void markTravelArchived(long travelId) {
		travelRepository.deleteTravelDays(travelId);
		travelRepository.deleteDays(travelId);
		travelRepository.deleteParticipants(travelId);
		travelRepository.deleteTravelSettingsAssociation(travelId);
		travelRepository.removeTravelSettings();
		travelRepository.deleteById(travelId);
	}
}
