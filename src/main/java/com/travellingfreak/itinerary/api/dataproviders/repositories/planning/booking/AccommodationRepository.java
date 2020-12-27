package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.AccommodationDetails;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends CrudRepository<AccommodationDetails, Long> {
	
	

	  @Query("select a from AccommodationDetails a, BookingReference b where a.travel.id=:travelId and b.travel.id =:travelId ")
	  Page<AccommodationDetails> findAccommoodations(long travelId, Pageable pageRequest); 
	  
	  
	  @Query("select a from AccommodationDetails a where a.name=:name and (a.travel.id=:travelId or  a.travel.id = null) ")
	  Page<AccommodationDetails> searchAccommoodations(long travelId,String name, Pageable pageRequest); 
	  
	  

	  @Query("select a from AccommodationDetails a where a.id=:id and a.travel.id=:travelId")
	  AccommodationDetails findAccommoodation(long travelId,long id); 
	  
	  @Query("select a from AccommodationDetails a where a.id=:id and a.travel.id=:travelId and a.isCustom=true ")
	  AccommodationDetails CustomAccommoodation(long travelId,long id); 
	  




}
