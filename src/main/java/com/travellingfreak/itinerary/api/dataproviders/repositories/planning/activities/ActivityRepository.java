package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Bucket;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {


 


  @Modifying
  @Transactional
  @Query(value = "update activities  set price_id = null where price_id =:Id", nativeQuery = true)
  void deleteReferenceToCost(long Id);


  @Modifying
  @Transactional
  @Query(value = "update activities set bucket_id=null, bucket_position=null where bucket_id=:bucketId", nativeQuery = true)
  void removebucket(long bucketId);

  @Modifying
  @Transactional
  @Query(value = "delete from activities where travel_id=:tripId", nativeQuery = true)
  void deleteActivities(long tripId);
  
  @Modifying
  @Transactional
  @Query(value = "delete from activity_likes where activity_id in (select id from activities where travel_id=:tripId )", nativeQuery = true)
  void deleteActivitieslikes(long tripId);
  
  @Modifying
  @Transactional
  @Query(value = "delete from travel_costs where parentactivity_id in (select id from activities where travel_id=:tripId )", nativeQuery = true)
  void deleteActivitiescosts(long tripId);


  @Modifying
  @Transactional
  @Query(value = "update activities set deleted=true where id=:activityId", nativeQuery = true)
  void deleteActivity(long activityId);


 

  

  @Modifying
  @Transactional
  @Query(value = "update activities set bucket_id=:bucketId, bucket_position=:bucketPosition where id=:activityId", nativeQuery = true)
  void moveactivity(long activityId, long bucketId, int bucketPosition);

 

  @Query("select a from Activity a where a.bucket.id=:bId and a.travel.id=:tId and a.bucketPosition=:p")
  Activity findActivityinBucket(long tId, long bId, int p);


  @Query(value = "select * from activities where id=:activityId", nativeQuery = true)
  Activity findActivity(long activityId);


  
  @Query(value = "select * from activities where bucket_id = :bucketId order by bucket_position ", nativeQuery = true)
  List<Activity> findActivities(long bucketId);

 
  @Query(value = "select * from activities where day_id=:Id", nativeQuery = true)
  List<Activity> findAllDayActivities(long Id);
  


  @Query(value = "select * from activities where day_id=:Id and starttime is null", nativeQuery = true)
  List<Activity> findAllDayActivitieswithoutStartTime(long Id);

  
  @Query(value = "select * from activities where day_id=:Id and starttime is not null and endtime is not null ", nativeQuery = true)
  List<Activity> findPlannedActivitieswithEndStartTime(long Id);


  @Query("select count(a) from Activity a  where a.bucket.id=:bId and a.travel.id=:tId")
  int countBuckets(long tId, long bId);

  @Modifying
  @Query("update Activity set bucketPosition = bucketPosition + :i where bucket = :bucket and bucketPosition >= :bucketPosition and travel = :travel")
  void updatePositionsBy(int i, Travel travel, Bucket bucket, Integer bucketPosition);

}
