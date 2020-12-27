package com.travellingfreak.itinerary.api.dataproviders.repositories.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface TravelRepository extends CrudRepository<Travel, Long> {
  @Query("select t from Travel t where ( t.mode = 'DRAFT' or (t.mode = 'PLANNING' and t.startDate > :currentDate)) and t.deleted = false and (t.createdBy = :username or :username in (select p.accountId from Participant p where p.travel = t))")
  Page<Travel> findUpcomingTravels(String username, LocalDate currentDate, Pageable pageRequest);

  @Query("select t from Travel t where (t.deleted = true or (t.mode = 'PLANNING' and t.startDate <= :currentDate)) and (t.createdBy = :username or :username in (select p.accountId from Participant p where p.travel = t))")
  Page<Travel> findArchivedTravels(String username, LocalDate currentDate, Pageable pageRequest);
  
  @Query("select d from Day d where d.travel.id = :Id")
  List<Day> findTravelDays(long Id);
  
  @Transactional
  @Modifying
  @Query(value= "update travels set deleted = true where id= :travelId ", nativeQuery=true)
  void markTravelArchived(long travelId);
  
  @Transactional
  @Modifying
  @Query(value= "delete from travels_days where travel_id= :travelId ", nativeQuery=true)
  void deleteTravelDays(long travelId);
  
  @Transactional
  @Modifying
  @Query(value= "delete from days where travel_id= :travelId ", nativeQuery=true)
  void deleteDays(long travelId);
  
  @Transactional
  @Modifying
  @Query(value= "delete from participants where travel_id= :travelId ", nativeQuery=true)
  void deleteParticipants(long travelId);
  
  
  
  @Transactional
  @Modifying
  @Query(value= " update travel_settings set travel_id = null where travel_id= :travelId ", nativeQuery=true)
  void deleteTravelSettingsAssociation(long travelId);
  
  @Transactional
  @Modifying
  @Query(value= "delete from travel_settings  where travel_id=null ", nativeQuery=true)
  void removeTravelSettings();
  
  
  
  
  
  
}
