package com.travellingfreak.itinerary.api.core.planning;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.TravelSetting;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelSettingRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchTravelSettingsRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class UpdateTravelSettingsUseCase {

	private final TravelRepository travelRepository;
	private final TravelSettingRepository travelSettingRepository;

	public void updateTravelSettings(final long travelId, final PatchTravelSettingsRequest request) {

		Travel t = travelRepository.findById(travelId).get();
		final var body = request;
		TravelSetting ts = travelSettingRepository.findById(t.getSettings().getId())
				.orElseThrow(EntityNotFoundException::new);

		if (body.getDefaultCurrency() != null) {
			ts.setDefaultCurrency(body.getDefaultCurrency());
		}
		if (body.getTemperatureMeasureUnit() != null) {
			ts.setTemperatureMeasureUnit(body.getTemperatureMeasureUnit());
		}
		if (body.getDistanceUnit() != null) {
			ts.setDistanceUnit(body.getDistanceUnit());
		}
		if (body.getMaximumActivitiesPerDay() != null) {
			ts.setMaximumActivitiesPerDay(body.getMaximumActivitiesPerDay());
		}
		if (body.getPlanningType() != null) {
			ts.setPlanningType(body.getPlanningType());
		}
		if (body.getDefaultTransportMode() != null) {
			ts.setDefaultTransportMode(body.getDefaultTransportMode());
		}
		if (body.getAllowDiscussions() != null) {
			ts.setAllowGuestsToInvite(body.getAllowDiscussions());
		}
		if (body.getAllowFileSharing() != null) {
			ts.setAllowFileSharing(body.getAllowFileSharing());
		}
		if (body.getAllowDiscussions() != null) {
			ts.setAllowDiscussions(body.getAllowDiscussions());
		}
		if (body.getAllowEmailProcessing() != null) {
			ts.setAllowEmailProcessing(body.getAllowEmailProcessing());
		}
		if (body.getDayStartsAt() != null) {
			ts.setDayStartsAt(body.getDayStartsAt());
		}
		if (body.getDayEndsAt() != null) {
			ts.setDayEndsAt(body.getDayEndsAt());
		}
		if (body.getDailyBudget() != null) {
			ts.setDailyBudget(body.getDailyBudget());
		}
		if (body.getBudget() != null) {
			ts.setBudget(body.getBudget());
		}
		if (body.getBudgetCurrency() != null) {
			ts.setBudgetCurrency(body.getBudgetCurrency());
		}
		if (body.getVisibility() != null) {
			t.setVisibility(body.getVisibility());
			travelRepository.save(t);
		}

		travelSettingRepository.save(ts);

	}

}
