package com.travellingfreak.itinerary.api.core.planning;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.travellingfreak.itinerary.api.core.model.Counter;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.PlanningMode;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.DaySettings;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.DayRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.PostRepository;
import com.travellingfreak.itinerary.api.entrypoints.requests.PatchTravelRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class UpdateTravelBasicData {
	private final PostRepository postRepository;
	private final TravelRepository travelRepository;
	private final DayRepository dayRepository;

	@Transactional
	public void updateTravelBasicData(final String username, final long travelId, final PatchTravelRequest request) {

		Travel t = travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);

		final var body = request;
		if (body.getTitle() != null) {
			t.setTitle(body.getTitle());
		}
		Post p = t.getDescription();

		if (body.getDescription() != null) {
			if (p != null) {

				p.setMessage(body.getDescription());
				p.setLastModifiedBy(username);
				p.setLastModifiedDate(LocalDateTime.now());
				postRepository.save(p);
				t.setDescription(p);
			} else {

				p = new Post();
				p.setCreatedBy(username);
				p.setMessage(body.getDescription());
				postRepository.save(p);
				t.setDescription(p);

			}
		}

		if (body.getMode() != null) {
			t.setMode(body.getMode());
		}
		if (body.getCoverPhoto() != null) {
			t.setCoverPhoto(body.getCoverPhoto());
		}

		if (body.getMode() == PlanningMode.PLANNING || (body.getStartDate() != null && body.getEndDate() != null)) {

			if (t.getStartDate() == null && t.getEndDate() == null) {
				final var counter = new Counter();
				body.getStartDate().datesUntil(body.getEndDate()).forEach(it -> {
					counter.inc();
					final var count = counter.getCount();
					t.addDay(new Day(0L, t, count, it, "Day " + count, new DaySettings(), new ArrayList<>()));
				});
			} else if (t.getStartDate() != null && t.getEndDate() != null) {

				int c1 = (int) ChronoUnit.DAYS.between(t.getStartDate(), t.getEndDate());
				int c2 = (int) ChronoUnit.DAYS.between(body.getStartDate(), body.getEndDate());

				if (t.getStartDate().equals(body.getStartDate())) {
					int c3 = c2 - c1;
					if (c3 > 0) {
						final var counter = new Counter();

						t.getEndDate().plusDays(1).datesUntil(body.getEndDate().plusDays(1)).forEach(it -> {
							counter.inc();
							final var count = counter.getCount() + c1 + 1;
							t.addDay(new Day(0L, t, count, it, "Day " + count, new DaySettings(), new ArrayList<>()));
						});

					}

					else if (c3 < 0) {

						body.getEndDate().plusDays(1).datesUntil(t.getEndDate().plusDays(1)).forEach(it -> {

							dayRepository.removeDayfromActivities(it, t.getId());
							dayRepository.removeTravelDays(it, t.getId());
							dayRepository.removeDay(it, t.getId());

						});

					}
				} else if (t.getEndDate().equals(body.getEndDate())) {

					int c3 = c2 - c1;
					if (c3 > 0) {
						final var counter = new Counter();

						body.getStartDate().datesUntil(t.getStartDate()).forEach(it -> {
							counter.inc();
							final var count = counter.getCount();
							t.addDay(new Day(0L, t, count, it, "Day " + count, new DaySettings(), new ArrayList<>()));
						});

						t.getStartDate().datesUntil(t.getEndDate().plusDays(1)).forEach(it -> {

							counter.inc();
							final var count = counter.getCount();

							dayRepository.updateDay(count, "Day " + count, it, t.getId());

						});

					}

					else if (c3 < 0) {

						t.getStartDate().datesUntil(body.getStartDate()).forEach(it -> {

							dayRepository.removeDayfromActivities(it, t.getId());
							dayRepository.removeTravelDays(it, t.getId());
							dayRepository.removeDay(it, t.getId());

						});

						final var counter1 = new Counter();
						body.getStartDate().datesUntil(t.getEndDate().plusDays(1)).forEach(it -> {
							counter1.inc();
							final var count = counter1.getCount();

							dayRepository.updateDay(count, "Day " + count, it, t.getId());

						});

					}

				} else if (!(t.getEndDate().equals(body.getEndDate()))
						&& !(t.getStartDate().equals(body.getStartDate()))) {

					int c3 = c2 - c1;
					if (c3 > 0) {
						final var counter = new Counter();

						t.getStartDate().datesUntil(t.getEndDate().plusDays(1)).forEach(it -> {

							counter.inc();
							final var count = counter.getCount();

							dayRepository.updateDayDate(body.getStartDate().plusDays(count - 1), it, t.getId());

						});

						body.getStartDate().plusDays(c1 + 1).datesUntil(body.getEndDate().plusDays(1)).forEach(it -> {

							final var count = counter.getCount() + 1;
							System.out.println(count);
							t.addDay(new Day(0L, t, count, it, "Day " + count, new DaySettings(), new ArrayList<>()));
							counter.inc();
						});

					}

					else if (c3 < 0) {

						final var counter = new Counter();

						t.getStartDate().plusDays(c2 + 1).datesUntil(t.getEndDate().plusDays(1)).forEach(it -> {

							dayRepository.removeDayfromActivities(it, t.getId());
							dayRepository.removeTravelDays(it, t.getId());
							dayRepository.removeDay(it, t.getId());

						});

						t.getStartDate().datesUntil(t.getStartDate().plusDays(c2 + 1)).forEach(it -> {

							counter.inc();
							final var count = counter.getCount();
							System.out.println(count);
							System.out.println(it);

							dayRepository.updateDayDate(body.getStartDate().plusDays(count - 1), it, t.getId());

						});

					} else {

						final var counter = new Counter();

						t.getStartDate().datesUntil(t.getEndDate().plusDays(1)).forEach(it -> {

							counter.inc();
							final var count = counter.getCount();
							dayRepository.updateDayDateexisting(body.getStartDate().plusDays(count - 1), it, t.getId(),
									count);

						});

					}

				}

			}

			t.setStartDate(body.getStartDate());
			t.setEndDate(body.getEndDate());

		} else if (body.getMode() == PlanningMode.DRAFT || body.getNumberOfDays() != 0) {

			t.setStartDate(null);
			t.setEndDate(null);
			int count = dayRepository.countDays(travelId);
			if (count <= body.getNumberOfDays()) {
				int diff = body.getNumberOfDays() - count;
				for (int i = 1; i < (diff + 1); i++) {
					t.addDay(new Day(0L, t, (i + count), null, "Day " + (i + count), new DaySettings(),
							new ArrayList<>()));
				}
			} else {

				int diff = body.getNumberOfDays() + 1;
				for (int i = diff; i < (count + 1); i++) {

					dayRepository.removeDraftDaysAssociation(i, t.getId());
					dayRepository.removeDraftDays(i, t.getId());
				}

			}
		}

		t.setLastModifiedBy(username);
		t.setLastModifiedDate(LocalDateTime.now());
		travelRepository.save(t);

	}
}
