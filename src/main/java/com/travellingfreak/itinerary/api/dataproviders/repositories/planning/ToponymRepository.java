package com.travellingfreak.itinerary.api.dataproviders.repositories.planning;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.travellingfreak.itinerary.api.dataproviders.model.geo.Toponym;

@Repository
public interface ToponymRepository extends CrudRepository<Toponym, Long> {
	
	@Query("select c from Toponym c, Travel t where t.deleted = false and t.id= :travelId and c.name= :searchText")
	  Page<Toponym> findCitiesOrderesbyPopulation(long travelId, String searchText, Pageable pageRequest);

	
	@Query("select c from Toponym c, Travel t where t.deleted = false and t.id= :travelId ")
	  Page<Toponym> findCitiesOrderesbyPopulation(long travelId, Pageable pageRequest);
	
	
	@Transactional
	@Modifying
	@Query(value="select * from geonames  where id in (select location_id from travel_toponyms where travel_id=:travelId)", nativeQuery=true)
	List<Toponym> findCitiesforTravel(long travelId);


	  
	
}
