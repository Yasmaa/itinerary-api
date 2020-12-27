package com.travellingfreak.itinerary.api.dataproviders.repositories.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

	
	@Query("select c from Location c,Travel t, Day d where c.name=:searchText and t.id=:travelId and d.travel=:travelId and d.id=:dayId ")
	Page<Location> findplacebynamenday(long travelId, long dayId,String searchText, Pageable pageRequest);

	
	@Query("select c from Location c where c.name=:searchText ")
	Page<Location> findplacebyname(String searchText, Pageable pageRequest);

	  
	
}
