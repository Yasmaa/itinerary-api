package com.travellingfreak.itinerary.api.core.planning;

import com.travellingfreak.itinerary.api.core.model.accounts.User;
import com.travellingfreak.itinerary.api.dataproviders.model.accounts.Account;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.AccountRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.accounts.ParticipantRepository;
import com.travellingfreak.itinerary.api.dataproviders.repositories.planning.TravelRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class FindFriendsToInviteUseCase {
	private final AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<User> listAccounts(final long travelId, final String name) {

		return accountRepository.findaccountbyname(travelId, name).stream()

				.map(this::convertToUser).collect(Collectors.toList());
	}

	private User convertToUser(Account ac) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		User user = modelMapper.map(ac, User.class);
		return user;

	}

}
