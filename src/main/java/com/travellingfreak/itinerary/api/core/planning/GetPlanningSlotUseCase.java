package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.BookingReference;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.LikeRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DaySettingsRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.BookingQuickView;
import com.travellingfreak.itinerary.api.entrypoints.responses.CalendarActivity;
import com.travellingfreak.itinerary.api.entrypoints.responses.DayMapView;
import com.travellingfreak.itinerary.api.entrypoints.responses.DayOverviewDetails;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GetPlanningSlotUseCase {

	private final ActivityRepository activityRepository;
	private final DayRepository dayRepository;
	private final CostRepository costRepository;
	private final LikeRepository likeRepository;
	private final BookingReferenceRepository bookingReferenceRepository;
	private String currentUser;

	@Transactional
	public DayOverviewDetails getPlanningSlot(final String username, final long travelId, final long dayId) {

		currentUser = username;
		Day d = dayRepository.findById(dayId).orElseThrow(EntityNotFoundException::new);

		List<BookingQuickView> bookings = bookingReferenceRepository.findBookings(d.getDate(), travelId).stream()
				.map(this::convertToBookingView).collect(Collectors.toList());

		List<Activity> plannedActivities = activityRepository.findPlannedActivitieswithEndStartTime(dayId);
		List<Activity> allDayActivities = activityRepository.findAllDayActivities(dayId);

		List<CalendarActivity> pdActivities = plannedActivities.stream().map(this::convertToCalendarActivity)
				.collect(Collectors.toList());

		List<CalendarActivity> acActivities = allDayActivities.stream().map(this::convertToCalendarActivity)
				.collect(Collectors.toList());

		DayMapView dayMapView = new DayMapView();

		double estimatedCost = 0;
		int totalPlanHours = 0;
		int plannedHours = 0;

		for (Activity a : allDayActivities) {

			if (a.getPrice() != null) {

				Cost c = costRepository.findById(a.getPrice().getId()).get();
				estimatedCost += a.getPrice().getAmount();
			}

			if (a.getEndTime() != null && a.getStartTime() != null) {
				totalPlanHours += (a.getEndTime().getHour() - a.getStartTime().getHour());
			}

		}

		for (Activity a : plannedActivities) {

			if (a.getEndTime() != null && a.getStartTime() != null) {
				plannedHours += (a.getEndTime().getHour() - a.getStartTime().getHour());
			}
		}

		DayOverviewDetails dayOverviewDetails = new DayOverviewDetails();

		dayOverviewDetails.setAllDayActivities(acActivities);
		dayOverviewDetails.setPlannedActivities(pdActivities);

		dayOverviewDetails.setAlreadyPlannedHours(plannedHours);
		dayOverviewDetails.setTotalPlanHours(totalPlanHours);

		dayOverviewDetails.setEstimatedCost(estimatedCost);
		if (d.getSettings() != null) {
			dayOverviewDetails.setCurrency(d.getSettings().getDefaultCurrency());
		}

		dayOverviewDetails.setCreatedBy(new User(d.getCreatedBy(), null, null));
		dayOverviewDetails.setCreatedOn(d.getCreatedDate());

		if (d.getLastModifiedBy() != null) {
			dayOverviewDetails.setLastModifiedBy(new User(d.getLastModifiedBy(), null, null));
			dayOverviewDetails.setLastModifiedOn(d.getLastModifiedDate());
		} else {
			dayOverviewDetails.setLastModifiedBy(null);
		}

		dayOverviewDetails.setHotel(bookings);

		return dayOverviewDetails;

	}

	private BookingQuickView convertToBookingView(BookingReference br) {

		BookingQuickView bv = new BookingQuickView();
		bv.setId(br.getId());
		bv.setAccommodationName(br.getDetails().getName());
		bv.setStartDate(br.getStartDate());
		bv.setEndDate(br.getEndDate());

		return bv;

	}

	private CalendarActivity convertToCalendarActivity(Activity ac) {

		CalendarActivity calendarActivity = new CalendarActivity();
		calendarActivity.setTitle(ac.getTitle());
		calendarActivity.setId(ac.getId());
		calendarActivity.setStartTime(ac.getStartTime());
		calendarActivity.setEndTime(ac.getEndTime());

		calendarActivity.setDislikeCount(likeRepository.findCountDislikeReactions(ac.getId()));
		calendarActivity.setLikeCount(likeRepository.findCountLikeReactions(ac.getId()));

		calendarActivity.setCreatedBy(new User(ac.getCreatedBy(), null, null));

		calendarActivity.setCreatedOn(ac.getCreatedDate());

		Like like = likeRepository.findReactionForUser(currentUser, ac.getId());
		if (like != null) {

			if (like.isThumbsUp()) {
				calendarActivity.setCurrentUserLikes(true);
			} else {
				calendarActivity.setCurrentUserDislikes(true);
			}

		}

		if (ac.getLastModifiedBy() != null) {
			calendarActivity.setLastModifiedBy(new User(ac.getLastModifiedBy(), null, null));
			calendarActivity.setLastModifiedOn(ac.getLastModifiedDate());
		} else {
			ac.setLastModifiedBy(null);
		}

		return calendarActivity;

	}

}
