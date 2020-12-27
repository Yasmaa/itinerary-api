package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets;



import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Reply;

@Repository
public interface ReplyRepository extends PagingAndSortingRepository<Reply, Long> {
	
	@Modifying 
	@Transactional
	@Query(value = "select * from replies  where post_id=:Id order by created_date ASC", nativeQuery = true)
	List<Reply> replies(long Id);
	
	
	
	
	

}
