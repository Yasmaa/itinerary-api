package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.booking;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.BookingReference;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingReferenceRepository extends CrudRepository<BookingReference, Long> {

	 @Query("select b from BookingReference b where b.travel.id =:travelId ")
	  List<BookingReference> findBookings(long travelId); 
	 
	 
	 @Transactional
	 @Modifying
	 @Query(value="delete from bookings where details_id =:Id ",nativeQuery = true)
	 void deleteBookings(long Id);
	 
	 
	 
	 @Query("select b from BookingReference b where b.startDate <= :date and b.endDate >= :date and  b.travel.id =:travelId ")
	  List<BookingReference> findBookings(LocalDate date,long travelId); 
	  
	
	
}
