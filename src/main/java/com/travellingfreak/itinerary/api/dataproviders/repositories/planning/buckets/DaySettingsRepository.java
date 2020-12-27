package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.DaySettings;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaySettingsRepository extends CrudRepository<DaySettings, Long> {

	
	
	
	
	@Query("select d from DaySettings d where d.day.id = :dayId")
	DaySettings daySettings(long dayId);
	
	
	
	
	
	
}
