package com.travellingfreak.itinerary.api.dataproviders.repositories.accounts;

import com.travellingfreak.itinerary.api.dataproviders.model.accounts.Participant;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {

	@Query("select p from Participant p where p.travel.id=:travelId and p.accountId!= :username ")
	List<Participant> findOtherParticipants(String username,long travelId);
	
	@Query("select p from Participant p where p.travel.id=:travelId and p.accountId = :username ")
	Participant findParticipant(String username,long travelId);
	
	Page<Participant> findByTravel(Travel id, Pageable paging);

}
