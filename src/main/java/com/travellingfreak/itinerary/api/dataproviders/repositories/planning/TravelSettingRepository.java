package com.travellingfreak.itinerary.api.dataproviders.repositories.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.TravelSetting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelSettingRepository extends CrudRepository<TravelSetting, Long> {

}
