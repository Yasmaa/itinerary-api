package com.travellingfreak.itinerary.api.dataproviders.repositories.budgets;

import com.travellingfreak.itinerary.api.dataproviders.model.budget.BuyOption;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyOptionRepository extends CrudRepository<BuyOption, Long> {

}
