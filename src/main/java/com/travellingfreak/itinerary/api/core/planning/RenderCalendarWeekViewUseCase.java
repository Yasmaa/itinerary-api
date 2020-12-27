package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.LikeRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DaySettingsRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.CalendarActivity;
import com.travellingfreak.itinerary.api.entrypoints.responses.CalendarDayDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.CalendarWeekView;
import org.springframework.stereotype.Component;

import java.util.Calendar;
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
public class RenderCalendarWeekViewUseCase {
	private final ActivityRepository activityRepository;
	private final DaySettingsRepository daySettingRepository;
	private final DayRepository dayRepository;
	private final CostRepository costRepository;
	private final TravelRepository travelRepository;
	private final LikeRepository likeRepository;
	private String currentUser;
	private int Month;
	private int Year;
	private int Week;

	public CalendarWeekView getCalenderMonthView(final String username, final long travelId, final int month,
			final int year, final int week) {

		currentUser = username;
		Month = month;
		Year = year;
		Week = week;

		CalendarWeekView calendarWeekView = new CalendarWeekView();
		calendarWeekView.setMonth(Month);
		calendarWeekView.setYear(Year);

		Travel t = travelRepository.findById(travelId).get();

		calendarWeekView.setStartDate(t.getStartDate());
		calendarWeekView.setEndDate(t.getEndDate());

		int travelMonthStart = t.getStartDate().getMonthValue();
		int travelMonthEnd = t.getEndDate().getMonthValue();
		int travelYearStart = t.getStartDate().getYear();
		int travelYearEnd = t.getEndDate().getYear();

		if (travelMonthEnd >= month && travelMonthStart <= month && travelYearEnd >= year && travelYearStart <= year) {

			List<Day> monthDays = dayRepository.findTravelDays(t.getId());

			List<CalendarDayDetails> adActivities = monthDays.stream().map(this::convertToCalendarMonth)
					.filter(e -> e.getDayId() != 0).collect(Collectors.toList());

			calendarWeekView.setCalendarDays(adActivities);

		}

		return calendarWeekView;

	}

	private CalendarDayDetails convertToCalendarMonth(Day d) {

		CalendarDayDetails calendarDayDetails = new CalendarDayDetails();

		Calendar cl = Calendar.getInstance();
		cl.set(d.getDate().getYear(), d.getDate().getMonthValue() + 1, d.getDate().getDayOfMonth());
		if (d.getDate().getMonthValue() == Month && d.getDate().getYear() == Year
				&& cl.get(Calendar.WEEK_OF_MONTH) == Week) {

			List<CalendarActivity> allDayActivities = activityRepository.findAllDayActivitieswithoutStartTime(d.getId())
					.stream().map(this::convertToCalendarActivity).collect(Collectors.toList());

			List<CalendarActivity> plannedActivities = activityRepository
					.findPlannedActivitieswithEndStartTime(d.getId()).stream().map(this::convertToCalendarActivity)
					.collect(Collectors.toList());

			calendarDayDetails.setDayId(d.getId());
			calendarDayDetails.setDayNumber(d.getNumber());
			calendarDayDetails.setDayOfMonth(d.getDate().getDayOfMonth());

			calendarDayDetails.setAllDaysActivities(allDayActivities);
			calendarDayDetails.setPlannedActivities(plannedActivities);

		}

		return calendarDayDetails;

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
