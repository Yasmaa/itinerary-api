package com.travellingfreak.itinerary.api.dataproviders.repositories.accounts;

import com.travellingfreak.itinerary.api.dataproviders.model.accounts.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
  
  List<Account> findAllByIdIn(List<String> list);
  
  
  
  @Query("select a from Account a,Participant p where p.travel.id=:travelId and p.accountId=a.id and (lower(a.displayName)=lower(:name) or lower(a.email)=lower(:name)) ")
  List<Account> findaccountbyname(long travelId,String name);

  
  
  
  
}
