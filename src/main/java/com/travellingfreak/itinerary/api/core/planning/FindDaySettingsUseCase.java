package com.travellingfreak.itinerary.api.core.planning;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.TravelSetting;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.DaySettings;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelSettingRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DaySettingsRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.DaySetting;
import com.travellingfreak.itinerary.api.entrypoints.responses.TravelSettings;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class FindDaySettingsUseCase {
	private final TravelRepository travelRepository;
	private final TravelSettingRepository travelSettingsRepository;
	private final DayRepository dayRepository;
	private final DaySettingsRepository daySettingsRepository;

	public DaySetting findDaySettings(long travelId, long dayId) {

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
		TravelSetting ts = travelSettingsRepository.findById(t.getSettings().getId())
				.orElseThrow(EntityNotFoundException::new);
		Day d = dayRepository.findById(dayId).orElseThrow(EntityNotFoundException::new);
		DaySettings ds = new DaySettings();

		if (d.getSettings() == null) {

			ds = new DaySettings();
			ds.setDay(d);
			ds.setDefaultCurrency(ts.getDefaultCurrency());

			ds.setDistanceUnit(ts.getDistanceUnit());
			ds.setMaximumActivitiesPerDay(ts.getMaximumActivitiesPerDay());
			ds.setPlanningType(ts.getPlanningType());
			ds.setDefaultTransportMode(ts.getDefaultTransportMode());
			ds.setDayStartsAt(ts.getDayStartsAt());
			ds.setDayEndsAt(ts.getDayEndsAt());
			ds.setDailyBudget(ts.getDailyBudget());

			d.addSettings(ds);
			dayRepository.save(d);

		} else {
			ds = daySettingsRepository.findById(d.getSettings().getId()).orElseThrow(EntityNotFoundException::new);

			if (ds.getDefaultCurrency() == null) {
				ds.setDefaultCurrency(ts.getDefaultCurrency());
			}
			if (ds.getDistanceUnit() == null) {
				ds.setDistanceUnit(ts.getDistanceUnit());
			}
			if (ds.getMaximumActivitiesPerDay() == 0) {
				ds.setMaximumActivitiesPerDay(ts.getMaximumActivitiesPerDay());
			}
			if (ds.getPlanningType() == null) {
				ds.setPlanningType(ts.getPlanningType());
			}
			if (ds.getDefaultTransportMode() == null) {
				ds.setDefaultTransportMode(ts.getDefaultTransportMode());
			}
			if (ds.getDayEndsAt() == null) {
				ds.setDayStartsAt(ts.getDayStartsAt());
			}
			if (ds.getDayEndsAt() == null) {
				ds.setDayEndsAt(ts.getDayEndsAt());
			}
			if (ds.getDailyBudget() == null) {
				ds.setDailyBudget(ts.getDailyBudget());
			}

			daySettingsRepository.save(ds);
		}

		TravelSettings trst = new TravelSettings(ts.getDefaultCurrency(), ts.getDistanceUnit(),
				ts.getMaximumActivitiesPerDay(), ts.getPlanningType(), ts.getDefaultTransportMode(), null,
				ts.isAllowGuestsToInvite(), ts.isAllowFileSharing(), ts.isAllowDiscussions(),
				ts.isAllowEmailProcessing(), ts.getDayStartsAt(), ts.getDayEndsAt(), ts.getDailyBudget());
		DaySetting daySt = new DaySetting(trst, ds.getDefaultCurrency(), ds.getDistanceUnit(),
				ds.getMaximumActivitiesPerDay(), ds.getPlanningType(), ds.getDefaultTransportMode(), ds.getDayEndsAt(),
				ds.getDayEndsAt(), ds.getDailyBudget());

		return daySt;

	}
}
