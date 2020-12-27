package com.travellingfreak.itinerary.api.dataproviders.repositories.accounts;

import com.travellingfreak.itinerary.api.dataproviders.model.accounts.AccountSetting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingRepository extends CrudRepository<AccountSetting, Long> {

}
