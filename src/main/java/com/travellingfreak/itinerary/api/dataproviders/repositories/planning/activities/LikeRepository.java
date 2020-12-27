package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {
	
	
	@Query("select count(l) from Like l where l.activity.id=:activityId and l.thumbsUp=true ")
	int  findCountLikeReactions(long activityId);
	@Query("select count(l) from Like l where l.activity.id=:activityId and thumbsUp=false")
	int  findCountDislikeReactions(long activityId);
	
	@Query("select l from Like l where l.activity.id=:activityId and l.createdBy =:username ")
	Like findReactionForUser(String username,long activityId);
	
	

	@Query("select l from Like l where l.activity.id=:activityId and thumbsUp=true ")
	List<Like> findActivityLikes(long activityId);
	
	@Query("select l from Like l where l.activity.id=:activityId and thumbsUp=false")
	List<Like> findActivityDislikes(long activityId);

}
