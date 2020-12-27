package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.DayMapView;
import com.travellingfreak.itinerary.api.entrypoints.responses.GeoTaggedActivity;
import com.travellingfreak.itinerary.api.entrypoints.responses.LatLng;
import com.travellingfreak.itinerary.api.entrypoints.responses.LatLngBounds;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GetPlanningSlotAsMapUseCase {
	private final AccommodationRepository accommodationRepository;
	private final ActivityRepository activityRepository;

	public DayMapView getPlanningSlotAsMap(final long travelId, final long dayId) {

		List<Activity> activities = activityRepository.findAllDayActivities(dayId);
		List<GeoTaggedActivity> list = activities.stream().map(this::convertToGeoTaggedActivity)
				.collect(Collectors.toList());

		DayMapView dayMapView = new DayMapView();

		if (activities.size() != 0) {

			List<Double> lat = new ArrayList<Double>();
			List<Double> lag = new ArrayList<Double>();

			for (Activity a : activities) {

				lat.add(a.getStartLocation().getLatitude());
				lat.add(a.getEndLocation().getLatitude());

			}
			;

			for (Activity a : activities) {

				lag.add(a.getStartLocation().getLongitude());
				lag.add(a.getEndLocation().getLongitude());

			}
			;

			LatLng topleft = new LatLng();
			topleft.setLat(Collections.max(lat));
			topleft.setLng(Collections.max(lag));

			LatLng bottomright = new LatLng();
			bottomright.setLat(Collections.min(lat));
			bottomright.setLng(Collections.min(lag));

			LatLngBounds latLngBounds = new LatLngBounds();
			latLngBounds.setTopLeft(topleft);
			latLngBounds.setBottomRight(bottomright);

			dayMapView.setActivities(list);
			dayMapView.setMapBoundaries(latLngBounds);

		}
		return dayMapView;

	}

	private GeoTaggedActivity convertToGeoTaggedActivity(Activity ac) {

		GeoTaggedActivity geoTaggedActivity = new GeoTaggedActivity();

		geoTaggedActivity.setTitle(ac.getTitle());
		geoTaggedActivity.setActivityCategory(ac.getCategory());
		User user = new User();
		user.setName(ac.getCreatedBy());
		geoTaggedActivity.setCreatedBy(user);

		LatLng latLng = new LatLng();
		latLng.setLat(ac.getStartLocation().getLatitude());
		latLng.setLng(ac.getStartLocation().getLongitude());
		geoTaggedActivity.setLocation(latLng);

		return geoTaggedActivity;

	}

}
