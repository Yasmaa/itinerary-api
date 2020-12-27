package com.travellingfreak.itinerary.api.dataproviders.repositories.budgets;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends CrudRepository<Cost, Long> {
	
	
	 @Query("select c from Cost c where c.parentActivity.id=:activityId and c.travel.id=:tripId")
	  Page<Cost> getAllcostsforActivity(long tripId,long activityId, Pageable pageRequest); 
	  

	

}
