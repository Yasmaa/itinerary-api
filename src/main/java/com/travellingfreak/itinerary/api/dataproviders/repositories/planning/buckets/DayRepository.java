package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {

	@Modifying 
	@Transactional
	@Query(value = "delete from travels_days where days_id=:Id", nativeQuery = true)
	void removeAssociation(long Id);
	
	
	
	@Modifying 
	@Transactional
	@Query(value = "update day_posts set day_id=:newDayId where day_id=:dayId", nativeQuery = true)
	void moveposts(long dayId,long newDayId);
	
	@Modifying 
	@Transactional
	@Query(value = "insert into day_posts (day_id,post_id) values (:dayId,:postId)", nativeQuery = true)
	void addpostday(long dayId,long postId);
	
	@Modifying 
	@Transactional
	@Query(value = "update activities set day_id=:newDayId where day_id=:dayId", nativeQuery = true)
	void updateActivityDay(long dayId,long newDayId);
	
	@Modifying 
	@Transactional
	@Query(value = "update activities set day_id=null where day_id=:dayId", nativeQuery = true)
	void removeIdfromActivities(long dayId);
	
	@Modifying 
	@Transactional
	@Query(value = "delete from day_posts where day_id=:dayId", nativeQuery = true)
	void removeAssociationToPosts(long dayId);
	
	@Modifying 
	@Transactional
	@Query(value = "delete from activities  where day_id=:dayId", nativeQuery = true)
	void deleteAllActivities(long dayId);
	
	@Modifying 
	@Transactional
	@Query(value = "update activities set bucket_id=:bucketId where day_id=:dayId", nativeQuery = true)
	void addActivitiestoBucketList(long dayId,long bucketId);
	
	
	@Modifying 
	@Transactional
	@Query(value = "select * from days d, travels t where DATE_TRUNC('month',day_date)=DATE_TRUNC('month', start_date) and t.id = :Id and t.id= d.travel_id", nativeQuery = true)
	List<Day> findMonthDays(long Id);
	
	
	
	@Query(value = "select count(d) from Day d where d.travel.id=:Id")
	int countDays(long Id);
	
	@Query("select d from Day d where d.travel.id=:Id")
	List<Day> findTravelDays(long Id);
	
	
	@Modifying 
	@Transactional
	@Query("delete from Day d where d.date=:date and d.travel.id=:Id")
	void removeDay(LocalDate date, long Id);
	
	
	@Modifying 
	@Transactional
	@Query(value = "delete from travels_days where days_id in (select id from days where day_date=:date and travel_id =:Id)", nativeQuery = true)
	void removeTravelDays(LocalDate date, long Id);
	
	@Modifying 
	@Transactional
	@Query(value = "update activities set day_id = null  where day_id in (select id from days where day_date=:date and travel_id =:Id)", nativeQuery = true)
	void removeDayfromActivities(LocalDate date, long Id);
	
	
	
	@Modifying 
	@Transactional
	@Query("update Day d set d.number = :number, d.title = :title  where d.date=:date and d.travel.id=:Id")
	void updateDay(int number, String title,LocalDate date, long Id);
	
	
	@Modifying 
	@Transactional
	@Query("update Day d set d.date=:newDate where d.travel.id=:Id and d.date=:oldDate")
	void updateDayDate(LocalDate newDate,LocalDate oldDate, long Id);
	
	

	@Modifying 
	@Transactional
	@Query("update Day d set d.date=:newDate where number=:number and d.travel.id=:Id and d.date=:oldDate")
	void updateDayDateexisting(LocalDate newDate,LocalDate oldDate, long Id, int number);
	
	

	@Modifying 
	@Transactional
	@Query(value = "delete from travels_days where days_id in (select id from days where day_number=:number and travel_id =:Id)", nativeQuery = true)
	void removeDraftDaysAssociation(int number, long Id);
	
	@Modifying 
	@Transactional
	@Query(value = "delete  from days where day_number=:number and travel_id =:Id", nativeQuery = true)
	void removeDraftDays(int number, long Id);
	
	
	
	
	
	
	
	
	
}
