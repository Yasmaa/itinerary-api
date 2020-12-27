package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.core.model.geo.City;
import com.travellingfreak.itinerary.api.dataproviders.model.accounts.Participant;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.PlanningMode;
import com.travellingfreak.itinerary.api.dataproviders.model.geo.Toponym;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.TravelSetting;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.ParticipantRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.ToponymRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelSettingRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DaySettingsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.CalendarActivity;
import com.travellingfreak.itinerary.api.entrypoints.responses.Collapsedpost;
import com.travellingfreak.itinerary.api.entrypoints.responses.ReplyDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.TravelDetails;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class FindTravelDetailsUseCase {

	private final ActivityRepository activityRepository;
	private final TravelRepository travelRepository;
	private final TravelSettingRepository travelSettingRepository;
	private final ReplyRepository replyRepository;
	private final ToponymRepository toponymRepository;
	private final ParticipantRepository participantRepository;
	private final DayRepository dayRepository;

	@Autowired
	private ModelMapper modelMapper;

	public TravelDetails findTravelDetails(final String username, final long travelId) {

		TravelDetails travelDetails = new TravelDetails();
		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);

		if(t.getSettings()!=null) {
			TravelSetting ts = travelSettingRepository.findById(t.getSettings().getId()).get();
			travelDetails.setBudget(ts.getBudget());
			travelDetails.setBudgetCurrency(ts.getBudgetCurrency());
			
		}
		
		travelDetails.setId(t.getId());
		travelDetails.setTripName(t.getTitle());

		Collapsedpost test = new Collapsedpost();
		if (t.getDescription() != null) {
			test.setPost(t.getDescription());

			List<Reply> replies = replyRepository.replies(t.getDescription().getId());
			Integer count = replies.size();

			if (count != 0) {

				modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
				ReplyDetails rp = modelMapper.map(replies.get(count - 1), ReplyDetails.class);

				test.setLastreply(rp);
			}
			test.setCount(count);

			travelDetails.setDescription(test);
		}
		travelDetails.setStartDate(t.getStartDate());
		travelDetails.setEndDate(t.getEndDate());

		List<Toponym> toponyms = toponymRepository.findCitiesforTravel(travelId);

		List<City> cities = toponyms.stream().map(this::convertToCity).collect(Collectors.toList());

		travelDetails.setCities(cities);

		List<Participant> oparticipants = participantRepository.findOtherParticipants(username, travelId);
		List<User> otherParticipants = oparticipants.stream().map(this::convertToUser).collect(Collectors.toList());

		travelDetails.setOtherParticipants(otherParticipants);
		travelDetails.setVisibility(t.getVisibility());
		if (username.equals(t.getCreatedBy())) {
			travelDetails.setOwner(true);
		}
		
		if (participantRepository.findParticipant(username, travelId) != null) {
			travelDetails.setParticipant(true);
			travelDetails.setParticipantlevel(participantRepository.findParticipant(username, travelId).getRole());
		}
		
		travelDetails.setMode(t.getMode());
		if(t.getMode()==PlanningMode.DRAFT) {
			travelDetails.setNumberOfDays(dayRepository.countDays(travelId));
			
		}

		travelDetails.setCreatedBy(new User(t.getCreatedBy(), null, null));
		travelDetails.setLastModifiedBy(new User(t.getLastModifiedBy(), null, null));
		travelDetails.setCreatedOn(t.getCreatedDate());
		travelDetails.setLastModifiedOn(t.getLastModifiedDate());
		return travelDetails;

	}

	private City convertToCity(Toponym ac) {
		City c = new City();
		c.setId(ac.getId());
		c.setName(ac.getName());
		return c;

	}

	private User convertToUser(Participant ac) {
		User p = new User();
		p.setId(ac.getAccountId());
		return p;

	}

	private CalendarActivity convertToCalendarActivity(Activity ac) {

		CalendarActivity calendarActivity = new CalendarActivity();
		calendarActivity.setTitle(ac.getTitle());
		calendarActivity.setId(ac.getId());
		calendarActivity.setStartTime(ac.getStartTime());
		calendarActivity.setEndTime(ac.getEndTime());

		return calendarActivity;

	}

}
