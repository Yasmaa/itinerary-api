package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Bucket;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BucketsRepository extends CrudRepository<Bucket, Long> {
	
	
	
	
	
	
	
	
	@Query("select b from Bucket b where travel_id=:Id")
	List<Bucket> findBucketList(long Id);
	
	
	
	@Transactional
	@Modifying
	@Query("delete from Bucket b where travel_id=:Id")
	void deleteBucketList(long Id);
	
	
	
	
	

}
