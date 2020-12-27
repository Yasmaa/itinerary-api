package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Bucket;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;
import com.travellingfreak.itinerary.api.dataproviders.repositories.budgets.CostRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities.ActivityRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.AccommodationRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking.BookingReferenceRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.BucketsRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets.ReplyRepository;
import com.travellingfreak.itinerary.api.entrypoints.responses.ActivityDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.BucketListColumnDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.BucketListDetails;
import com.travellingfreak.itinerary.api.entrypoints.responses.Collapsedpost;
import com.travellingfreak.itinerary.api.entrypoints.responses.ReplyDetails;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Date: 7/19/2020 Time: 9:49 PM
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class GetBucketListforTripUseCase {
	private final BucketsRepository bucketsRepository;
	private final ActivityRepository activityRepository;
	private final ReplyRepository replyRepository;

	private final GetAccommodationsUseCase acc;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public BucketListDetails listBuckets(final long travelId) {

		BucketListDetails bucketListDetails = new BucketListDetails();

		List<BucketListColumnDetails> list = bucketsRepository.findBucketList(travelId).stream()

				.map(this::convert).collect(Collectors.toList());

		bucketListDetails.setColumns(list);

		return bucketListDetails;

	}

	private BucketListColumnDetails convert(Bucket b) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		BucketListColumnDetails bl = new BucketListColumnDetails();
		bl.setId(b.getId());
		bl.setTitle(b.getName());

		List<ActivityDetails> list = activityRepository.findActivities(b.getId()).stream()
				.map(this::convertToActivityDetails).collect(Collectors.toList());

		bl.setActivities(list);

		return bl;
	}

	private ActivityDetails convertToActivityDetails(Activity a) {

		ActivityDetails ad = new ActivityDetails();

		ad.setId(a.getId());
		ad.setCategory(a.getCategory());
		ad.setSubCategory(a.getSubCategory());
		ad.setTitle(a.getTitle());

		if (a.getPost() != null) {

			Collapsedpost cp = new Collapsedpost();

			List<Reply> replies = replyRepository.replies(a.getPost().getId());
			Integer count = replies.size();
			cp.setPost(a.getPost());
			if (!replies.isEmpty()) {
				modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
				ReplyDetails rp = modelMapper.map(replies.get(count - 1), ReplyDetails.class);
				cp.setLastreply(rp);
			}
			cp.setCount(count);

			ad.setDescription(cp);
		}
		ad.setStartTime(a.getStartTime());
		ad.setEndTime(a.getEndTime());
		if (a.getStartLocation() != null) {
			ad.setStartLocation(a.getStartLocation().getId());
		}
		if (a.getEndLocation() != null) {
			ad.setEndLocation(a.getEndLocation().getId());
		}
		ad.setDistanceCovered(a.getDistanceCovered());
		ad.setFlightNumber(a.getFlightNumber());
		ad.setGate(a.getGate());
		if (a.getPrice() != null) {
			ad.setPrice(a.getPrice().getAmount());
		}
		if (a.getPass() != null) {
			ad.setPass(a.getPass().getId());
		}

		return ad;

	}

}
