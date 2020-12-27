package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.buckets;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	
	@Modifying 
	@Transactional
	@Query(value = "insert into day_posts (day_id,post_id) values (:day_id,:post_id)", nativeQuery = true)
	void test(long day_id,long post_id);
	
	
	
	
	
	@Modifying 
	@Transactional
	@Query(value = "delete from day_posts where post_id=:Id", nativeQuery = true)
	void deletePostDayAssociation(long Id);
	
	
	@Query(value = "select * from posts where id in (select post_id from day_posts where day_id=:Id)", nativeQuery = true)
	Page<Post> findPostsforDay(long Id,Pageable pg);
	
	

}
